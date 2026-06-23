import java.io.File;

public class StorageUtil {
    public static String getStorageDir() {
        // 1️⃣ check if you explicitly set an environment variable
        String env = System.getenv("STORAGE_DIR");
        if (env != null && !env.isEmpty()) return env;

        // 2️⃣ detect OS and choose a default
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            return "D:/IDE/Storage";
        } else {
            return "/opt/quiz-storage";
        }
    }

    public static String usersFile() {
        return getStorageDir() + File.separator + "users.txt";
    }

    public static String scoresFile() {
        return getStorageDir() + File.separator + "scores.txt";
    }
}
