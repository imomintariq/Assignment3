import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String [] args) throws IOException {
        ArrayList<Word> wordsList = new ArrayList<Word>();
        List<FileHandler> tasks = new ArrayList<>();
        List<Thread> threads = new ArrayList<>();
        if(args.length > 0) {
            for(int i = 0; i <  args.length ; i++) {
                FileHandler task = new FileHandler(args[i]);
                Thread t = new Thread(task);
                t.start();
                tasks.add(task);
                threads.add(t);
            }
        }
        for(int i= 0; i< threads.size(); i++ ){
            try {
                threads.get(i).join();
                System.out.println("Thread joined");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for(int i = 0; i < tasks.size() ; i++){
            if(tasks.get(i).getFileName().compareTo("vocabulary.txt") == 0){
                for(int j = 0; j < tasks.size() ; j++){
                    if(tasks.get(j).getFileName().compareTo("vocabulary.txt") != 0){
                        tasks.get(j).matchWords(tasks.get(i).getMyTree(),wordsList);

                    }
                }
            }
        }



        Scanner input = new Scanner(System.in);
        int option = 0;
        while(option!= 5){
            System.out.println("1) Displaying BST build from Vocabulary File.\n" +
                    "2) Displaying Vectors build from Input files.\n" +
                    "3) Viewing Match words and its frequency\n" +
                    "4) Searching a query->It should display all the files query found in.\n" +
                    "5) Enter 5 for Exiting");
            option = input.nextInt();
            if(option == 1){
                for(int i = 0; i < tasks.size() ; i++){
                    if(tasks.get(i).getFileName().equals("vocabulary.txt")){
                        tasks.get(i).displayBST();
                    }
                }
            }
            if(option == 2){
                for(int i = 0; i < tasks.size() ; i++){
                    if(tasks.get(i).getFileName().compareTo("vocabulary.txt") != 0){
                        tasks.get(i).displayContainer();
                    }
                }
            }
            if(option == 3){
                System.out.println("Matched Words are:");
                for(int i = 0 ; i < wordsList.size();i++){
                    System.out.println(wordsList.get(i).getWord()+"     "+wordsList.get(i).getFrequency());
                }
            }
            if(option == 4){

                input.nextLine();
                System.out.println("Enter a query");

                String query = input.nextLine();

                for(int i = 0; i < tasks.size() ; i++){
                    if(tasks.get(i).getFileName().compareTo("vocabulary.txt") != 0) {
                        tasks.get(i).findQuery(query);
                    }

                }
            }
        }

    }
}
