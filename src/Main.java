import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static AtomicInteger three = new AtomicInteger(0);
    public static AtomicInteger four = new AtomicInteger(0);
    public static AtomicInteger five = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        String[] texts = new String[100_000];

        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        Thread palindrome = new Thread(() -> {
            for (String nickName : texts) {
                if (nickName.length() == 3 && palindromeCheck(nickName)) {
                    three.getAndIncrement();
                } else if (nickName.length() == 4 && palindromeCheck(nickName)) {
                    four.getAndIncrement();
                } else if (nickName.length() == 5 && palindromeCheck(nickName)) {
                    five.getAndIncrement();
                }
            }
        });
        palindrome.start();

        Thread sameLetterCheck = new Thread(() -> {
            for (String nickName : texts) {
                if (nickName.length() == 3 && sameLettersCheck(nickName)) {
                    three.getAndIncrement();
                } else if (nickName.length() == 4 && sameLettersCheck(nickName)) {
                    four.getAndIncrement();
                } else if (nickName.length() == 5 && sameLettersCheck(nickName)) {
                    five.getAndIncrement();
                }
            }
        });
        sameLetterCheck.start();

        Thread ascending = new Thread(() -> {
            for (String nickName : texts) {
                if (nickName.length() == 3 && ascendingCheck(nickName)) {
                    three.getAndIncrement();
                } else if (nickName.length() == 4 && ascendingCheck(nickName)) {
                    four.getAndIncrement();
                } else if (nickName.length() == 5 && ascendingCheck(nickName)) {
                    five.getAndIncrement();
                }
            }
        });
        ascending.start();

        palindrome.join();
        sameLetterCheck.join();
        ascending.join();

        System.out.println("Красивых слов с длиной 3: " + three + " шт" + "\n" +
                "Красивых слов с длиной 4: " + four + " шт" + "\n" +
                "Красивых слов с длиной 5: " + five + " шт");
    }

    public static boolean palindromeCheck (String nickname) {
        String clean = nickname.replaceAll("\\s+", "").toLowerCase();
        StringBuilder common = new StringBuilder(clean);
        StringBuilder reverse = common.reverse();
        return (reverse.toString()).equals(clean);
    }

    public static boolean sameLettersCheck (String name) {
        char c = name.charAt(0);
        for (int i = 0; i < name.length(); i++) {
            if (name.charAt(i) != c) {
                return false;
            }
        }
        return true;
    }

    public static boolean ascendingCheck(String name) {
        char previous = '\u0000';
        char[] string = name.toCharArray();
        for (char start : string) {
            if (start < previous)
                return false;
            previous = start;
        }
        return true;
    }
    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }
}
