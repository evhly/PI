public enum WeekDay {
    M(0),
    T(1),
    W(2),
    R(3),
    F(4);


    private int num;

    WeekDay(int num) {
        this.num = num;
    }

    String getName() {
        switch(num){
            case 0 :
                return "Monday";
            case 1 :
                return "Tuesday";
            case 2 :
                return "Wednesday";
            case 3 :
                return "Thursday";
        }
        return "Friday";
    }

    String getLetCode() {
        return getName().substring(0,1);
    }
}
