package Confluent;

public class SingleWildCardMatching {

//    "cat", "c*t"
//    "cat", "c*"
//    "cat", "*t"
    public boolean isMatch(String s, String pattern) {
        int p = 0;
        for (int i = 0; i < pattern.length(); i++) {
            if (pattern.charAt(i) == '*') {
                p = i;
                break;
            }
        }

        return comparePrefix(s, pattern.substring(0, p)) && compareSuffix(s, pattern.substring(p + 1));
    }

    public boolean comparePrefix(String s, String p) {
        if (p.isEmpty()) return true;
        int i = 0;
        int j = 0;

        while (i < s.length() && j < p.length()) {
            if (s.charAt(i) != p.charAt(j)) return false;
            i++;
            j++;
        }

        return j == p.length();
    }

    public boolean compareSuffix(String s, String p) {
        if (p.isEmpty()) return true;
        int i = s.length() - 1;
        int j = p.length() - 1;

        while (i >= 0 && j >= 0) {
            if (s.charAt(i) != p.charAt(j)) return false;
            i--;
            j--;
        }

        return j < 0;
    }

}
