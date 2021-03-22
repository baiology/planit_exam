

public class App {
    public static void main(String[] args) {
        String url = "https://jupiter.cloud.planittesting.com/#";
        String curr_dir = System.getProperty("user.dir");
        String filepath = curr_dir + "//test_cases.xlsx";
        String sheetname = args[0];
        System.out.println("Running test...");
        TestCases tc = new TestCases(url, filepath,sheetname);
        tc.runTest();
        System.out.println("Test Complete!");
        System.out.println("Refer to test case file for the status.");
        System.out.println(filepath);
    }
}
