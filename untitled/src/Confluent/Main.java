package Confluent;

public class Main {
    public static void main(String[] args) {
        searchPhraseTestCases();
    }

    public static void searchPhraseTestCases() {
        SearchPhrase searchPhrase = new SearchPhrase();

        searchPhrase.addDocument(1, "Cloud computing is the on-demand availability of computer system resources");
        searchPhrase.addDocument(2, "One integrated service for metrics uptime cloud monitoring dashboards and alerts reduces time spent navigating between systems");
        searchPhrase.addDocument(3, "Monitor entire cloud infrastructure, whether in the cloud computing is or in virtualized data centers");


        System.out.print("cloud -- ");
        searchPhrase.search("cloud").forEach(x -> System.out.print(x + ", "));
        System.out.println();
        System.out.print("cloud monitoring -- ");
        searchPhrase.search("cloud monitoring").forEach(x -> System.out.print(x + ", "));
        System.out.println();
        System.out.print("Cloud computing is -- ");
        searchPhrase.search("Cloud computing is").forEach(x -> System.out.print(x + ", "));
        System.out.println();
        System.out.print("yolo -- ");
        searchPhrase.search("yolo").forEach(x -> System.out.print(x + ", "));
        System.out.println();
        System.out.print("entire cloud infrastructure whether -- ");
        searchPhrase.search("entire cloud infrastructure whether").forEach(x -> System.out.print(x + ", "));
        System.out.println();
        System.out.print("Monitor entire cloud infrastructure, whether in the cloud computing is or in virtualized data centers -- ");
        searchPhrase.search("Monitor entire cloud infrastructure, whether in the cloud computing is or in virtualized data centers").forEach(x -> System.out.print(x + ", "));
        System.out.println();
        System.out.print(",,,, -- ");
        searchPhrase.search(",,,,").forEach(x -> System.out.print(x + ", "));
        System.out.println();
        System.out.print("cloud clout-- ");
        searchPhrase.search("cloud clout").forEach(x -> System.out.print(x + ", "));
        System.out.println();
    }
}
