import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Search {

    private ArrayList<Course> results;
    private String query;
    private CourseDatabase DB;
    private ArrayList<Filter> filters;
    private HashSet<String> wordSet;
    private HashMap<String, Integer> wordFreqMap;
    private HashMap<String, String> spellCheckedWords;
    private boolean suggestWordsFlag = false;

    App app = App.getInstance();

    public Search(CourseDatabase DB){
        this.DB = DB;
        this.filters = new ArrayList<>();
        results = new ArrayList<>();
        wordSet = getSetOfWords();
        if(suggestWordsFlag) {
            wordFreqMap = getWordFreqMap();
        }
        spellCheckedWords = new HashMap<>();
    }

    public boolean isSuggestWordsFlag(){
        return suggestWordsFlag;
    }

    /**
     *
     * @param query search query used to determine search results
     * @return search results returned when searching with the query
     */
    public ArrayList<Course> modifyQuery(String query){
        this.query = query.toLowerCase();
        return search();
    }

    public void addFilter(Filter filter) {
        filters.add(filter);
    }

    public void clearFilters() {
        this.filters = new ArrayList<>();
    }

    public ArrayList<Course> getResults() {
        return results;
    }

    /**
     * Searches the CourseDatabase for Courses that contain all query words within their course code, name, and meetings
     * Filters can be used to narrow the search to Courses that match department or Professor or meeting time
     * @return A list of Courses that match the query and filters
     */
    public ArrayList<Course> search(){
        results = new ArrayList<>();

        for(Course course : DB.getCourses()){ // checks every Course in the DB
            boolean filterMismatch = false; // this variable holds whether the filters rule a Course out
            for (Filter filter : filters) { // checks every filter

                if (filter.getType() == Filter.type.DEPARTMENT) {
                    if (!course.getDepartment().equals(filter.getDepartment())) { // checks for strict match
                        filterMismatch = true;
                    }
                }
                if ((filter.getType() == Filter.type.PROFESSOR) && !filterMismatch) {
                    if (!course.getProfessor().equals(filter.getProfessor())) { // checks for strict match
                        filterMismatch = true;
                    }
                }
                // if at least one day has a matching start and end time, then the course will be considered
                if ((filter.getType() == Filter.type.TIMES) && !filterMismatch) {
                    boolean hasMatchingTimes = false;

                    System.out.println(course.getMeetingTimes() + " == " +filter.getTimes()+"?"); //TODO: remove? (debugging?)


                    DateTimeFormatter ampmFormatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
                    for (DayOfWeek day : DayOfWeek.values()) { // for each day of the week
                        if (course.getMeetingTimes().containsKey(day)) { // if the course meets during that day
                            LocalTime filterStart = LocalTime.parse(filter.getTimes().get(0), ampmFormatter);
                            System.out.println("filter start" + filterStart);
                            LocalTime filterEnd = LocalTime.parse(filter.getTimes().get(1), ampmFormatter);
                            System.out.println("filter end" + filterEnd);
                            LocalTime courseStart = course.getMeetingTimes().get(day).get(0);
                            System.out.println("course start" + courseStart);
                            LocalTime courseEnd = course.getMeetingTimes().get(day).get(1);
                            System.out.println("course end" + courseEnd);
                            if ((courseStart.isAfter(filterStart) && courseEnd.isBefore(filterEnd))
                            || (courseStart.equals(filterStart))
                            || (courseEnd.equals(filterEnd))) {
                                hasMatchingTimes = true;
                            }
                        }
                    }
                    if (!hasMatchingTimes) {
                        filterMismatch = true;
                    }
                }
            }
            if (!filterMismatch) { // if the filters match, continue to check
                results.add(course);
            }
        }
        results = orderedResultsFromQuery();
        return results;
    }

    /**
     * Used for debugging
     * @return String representation of the current value of results
     */
    public String[] resultsStrs() {
        String[] arr = new String[results.size()];
        int i = 0;
        for (Course course : results) {
            arr[i] = course.getCode(); // unique identifier
            i++;
        }
        return arr;
    }

    public ArrayList<Course> searchBarSearch(String query, String department, Professor professor){
        ArrayList<Course> searchResults = new ArrayList<>();
        modifyQuery(query);
        Filter departmentFilterSelected = new Filter(Filter.type.DEPARTMENT, department);
        Filter facultyFilterSelected = new Filter(Filter.type.PROFESSOR, (Professor) professor);

        if(!Objects.equals(department, "")) {
            addFilter(departmentFilterSelected);
        }
        if(!Objects.equals(professor, new Professor("", ""))) {
            addFilter(facultyFilterSelected);
        }
        searchResults.addAll(search());
        return searchResults;
    }

    /**
     *
     * @return map containing the frequency of each word that appears in any course name in the DB
     * key: word in the course name
     * value: how many times it appears across all course names
     */
    public HashMap<String, Integer> getWordFreqMap(){
        // key: word
        // value: how many times it appears in course names
        HashMap<String, Integer> wordFreqs = new HashMap<>();

        for(Course course : DB.getCourses()){
            String[] words = course.getName().split(" ");
            for(String word : words){
                word = word.toLowerCase();
                wordFreqs.put(word, wordFreqs.getOrDefault(word, 0) + 1);
            }
        }
        return wordFreqs;
    }

    /**
     *
     * @param query word to suggest a completed word for
     * @return a suggested word if one is found, else null
     */
    public String suggestWord(String query) {
        ArrayList<String> matches = new ArrayList<>();
        // get all words in wordFreqMap that begin with query
        for (String s : wordFreqMap.keySet()) {
            if (s.indexOf(query) == 0) {
                matches.add(s);
            }
        }
        if (!matches.isEmpty() && matches.size() < 10) { // if a match exists and there are less than ten of them, return the match with the highest frequency
            String match = matches.getFirst();
            int count = wordFreqMap.get(match);
            for (int i = 1; i < matches.size(); i++) {
                if (wordFreqMap.get(matches.get(i)) > count) {
                    match = matches.get(i);
                    count = wordFreqMap.get(match);
                }
            }
            return match;
        }
        return null;
    }

    /**
     *
     * @return ArrayList of courses matching the query, ordered from most to least relevant to the query
     */
    private ArrayList<Course> orderedResultsFromQuery(){
        // key: relevance score
        // value: ArrayList of courses with that score
        TreeMap<Double, ArrayList<Course>> resultsMap = new TreeMap<>(Collections.reverseOrder());

        for(Course course : results){ // check each course in results to see if it matches the query
            double score = 0.0; // use to keep track of how well the query matches course
            String[] nameWords = course.getName().toLowerCase().split(" ");
            String[] queryWords = query.split(" ");
            for (String queryWord : queryWords) {
                if (!spellCheckedWords.containsKey(queryWord)) {
                    String match = getBestMatch(queryWord);
                    spellCheckedWords.put(queryWord, match);
                }
            }

            for(int i = 0; i < queryWords.length; i++){
                String queryWord = queryWords[i];
                double addToScore = 0.0;
                boolean spellChecked = false; // whether the original word from the user or its spell checked version is being used

                for(int j = 0; j < nameWords.length; j++){
                    String nameWord = nameWords[j];
                    int idx = nameWord.indexOf(queryWord);

                    // if queryWord is not contained in nameWord, try again using the spell checked version of queryWord
                    if(idx == -1){
                        String match = spellCheckedWords.get(queryWord);
                        if(match != null){
                            idx = nameWord.indexOf(match);
                            spellChecked = true;
                        }
                    }

                    if(idx != -1) {
                        if (queryWord.equals(nameWord)) {
                            if (j == 0) { // if queryWord equals first word in the course name
                                addToScore = 1;
                            } else {
                                addToScore = 0.9; // if queryWord equals a word other than the first in the course name
                            }
                            j = nameWords.length; // exit loop
                        } else { // if query is contained in nameWord, but not equal to
                            if (idx == 0) { // if it appears at the beginning of nameWord
                                if (j == 0) { // if it appears in the first word of the nameWord
                                    addToScore = 0.8;
                                } else if (addToScore < 0.7) { // if it appears in a nameWord other than the first
                                    addToScore = 0.7;
                                }
                            } else if (queryWord.length() > 1 && addToScore < 0.4) { // if query is inside of a word
                                addToScore = 0.4;
                            }
                        }
                    }
                }
                if(spellChecked){ // give slightly less favor to courses found when using spell check
                    addToScore -= .05;
                }
                if(addToScore <= 0.0){ // if queryWord is in no words of the course name, give a negative score
                    score -= 0.4;
                } else{
                    score += addToScore;
                }
            }
            score = score / queryWords.length; // normalize score according to length
            if(score > 0) { // only return results with a positive score
                if (resultsMap.containsKey(score)) {
                    resultsMap.get(score).add(course);
                } else {
                    ArrayList<Course> toAdd = new ArrayList<>();
                    toAdd.add(course);
                    resultsMap.put(score, toAdd);
                }
            }
        }

        ArrayList<Course> orderedResults = new ArrayList<>();
        for(ArrayList<Course> cArr : resultsMap.values()){
            orderedResults.addAll(cArr);
        }
        return orderedResults;
    }

    /**
     * Used for spell checking search querys
     * @param queryWord word to spell check, i.e. check if there is a close match for it among the words in wordSet
     * @return a sufficiently close match for queryWord from wordSet, or null if no such match exists
     */
    public String getBestMatch(String queryWord){
        char[] wordArr = queryWord.toLowerCase().toCharArray();
        if(queryWord.length() > 3) { // only spell check words length 4 or longer
            for (String s : wordSet) {
                String next = s.toLowerCase();
                if (Math.abs(queryWord.length() - next.length()) <= 2) { // the match cannot be more than 2 characters longer or shorter than queryWord
                    if (diffAlgorithm(wordArr, next.toCharArray()) <= 3) { // the match must be no more than 3 edits away from queryWord (see diffAlgorithm for explanation of edits)
                        return next;
                    }
                }
            }
        }
        return null;
    }

    /**
     *
     * @return set of all words found in the names of the course in DB
     */
    public HashSet<String> getSetOfWords(){
        HashSet<String> wordSet = new HashSet<>();
        for(Course course : DB.getCourses()){
            String[] words = course.getName().split(" ");
            for(String word : words){
                word = word.toLowerCase();
                wordSet.add(word);
            }
        }
        return wordSet;
    }

    /**
     * Java implementation of the Myers Diff algorithm, done by Allison Harnly in COMP422 Fall 2023
     *
     * @param A one of two sequences to find the edit difference between
     * @param B one of two sequences to find the edit difference between
     * @return the minimum number of edits needed to transform A into B
     * A single edit is either one deletion or one insertion of a character in a sequence
     */
    public int diffAlgorithm(char[] A, char[] B){
        int N = A.length;
        int M = B.length;
        int DMax = M + N;
        int[][] VArr = new int[DMax + 1][];
        int shiftV = DMax;

        int[] V = new int[2 * DMax + 1]; // At the end of the line 44 loop, V[i] always holds the furthest reaching D-path on diagonal i-VShift, if such a path exists
        V[shiftV + 1] = 0;

        int x = 0; // x value of the furthest point on the path currently being traced
        int y = 0; // y value of the furthest point on the path currently being traced

        // calculate the furthest reaching D-path for each possible diagonal and store these values in VArr[D]
        for (int D = 0; D <= DMax; D++) {

            // for each k, find the furthest reaching D-path on diagonal k
            for (int k = -D; k <= D; k += 2) {
                // determine if the furthest D-path on diagonal k will be an extension of the furthest (D-1)-path on diagonal k+1 or diagonal k-1
                if (k == -D || (k != D && V[shiftV + k - 1] < V[shiftV + k + 1])) {
                    x = V[shiftV + k + 1]; // if diagonal k+1, the current path extends one unit vertically downward from V[shiftV + k + 1]
                } else {
                    x = V[shiftV + k - 1] + 1; // if diagonal k-1, the current path extends one unit horizontally rightward from V[shiftV + k - 1]
                }

                y = x - k; // calculate the value of y at the point at the end of the current path

                // take the longest snake extending from (x,y)
                while (x < N && y < M && A[x] == B[y]) {
                    x++;
                    y++;
                }

                // add x coordinate to V array to keep record of the furthest reaching D-path on diagonal k
                V[shiftV + k] = x;

                // return the edit distance if the point (N,M) has been reached, as at this point the path corresponding to the LCS has been fully traced
                if (x >= N && y >= M) {
                    return D;
                }
            }
            // Add the V array for value D to VArr
            VArr[D] = V.clone();
        }
        // this next line should never run
        return -1;
    }
}
