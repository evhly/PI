import java.util.HashMap;
import java.util.Map;

public class PageManager {
    HashMap<String, Page> pages = new HashMap<>();

    public PageManager(){
        pages.put("login-page", new LoginPage(this));
        pages.put("new-account-page", new NewAccountPage(this));
    }

    public void switchPages(String pageName){
        Page currentPage = pages.get(pageName);
        currentPage.draw();
    }
}
