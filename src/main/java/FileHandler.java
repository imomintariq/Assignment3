import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;

public class FileHandler implements Runnable{
    private String fileName;
    private Vector<String> container;
    private BST_class myTree;

    public Vector<String> getContainer() {
        return container;
    }

    public void setContainer(Vector<String> container) {
        this.container = container;
    }

    public BST_class getMyTree() {
        return myTree;
    }

    public void setMyTree(BST_class myTree) {
        this.myTree = myTree;
    }

    public FileHandler(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }


    public void openCommandLineFiles(){

    }

    @Override
    public void run() {
        synchronized (this) {
            try {
                String currentDir = System.getProperty("user.dir");
                Path path = Paths.get(currentDir, fileName);
                File file = path.toFile();

                BufferedReader br = new BufferedReader(new FileReader(file));

                String line;


                //while(tokenizer.nextToken()!=null) {
                if (fileName.equals("vocabulary.txt")) {
                    myTree = new BST_class();
                    while ((line = br.readLine()) != null) {
                        StringTokenizer tokenizer = new StringTokenizer(line, " ");
                        while (tokenizer.hasMoreElements()) {
                            myTree.insert(tokenizer.nextToken());
                        }

                    }
                    //System.out.println("Tree isssss");
                    //myTree.inorder();
                    //System.out.print("\n");

                } else {
                    container = new Vector<String>();
                    while ((line = br.readLine()) != null) {
                        StringTokenizer tokenizer = new StringTokenizer(line, " ");
                        while (tokenizer.hasMoreElements()) {
                            container.add(tokenizer.nextToken());
                        }

                    }



                }

            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

    }
    public void displayContainer(){
        if(container!=null)
            System.out.println("Tokens in "+fileName+ " are " + container);
    }
    public void displayBST(){
        if(myTree != null) {
            System.out.println("BST is given below");
            myTree.inorder();
            System.out.println();
        }
    }

    public void matchWords(BST_class _myTree, ArrayList<Word> wordsList) {

        for(int i = 0; i < container.size();i++){
            if(_myTree.search(container.get(i))){
                boolean found = false;
                for(int j = 0; j  < wordsList.size() ; j++)
                {
                    if(wordsList.get(j).getWord().equals(container.get(i))){
                        found  = true;
                        wordsList.get(j).increaseFrequency();
                    }
                }
                if(found == false) {
                    Word word = new Word(container.get(i), 1);
                    wordsList.add(word);
                }
            }
        }
    }

    public void findQuery(String query) {
        ArrayList<Word> wordList = new ArrayList<Word>();
        StringTokenizer tokenizer = new StringTokenizer(query, " ");
        int total_matches = 0;
        while (tokenizer.hasMoreElements()) {
            String s = tokenizer.nextToken();
            boolean found = false;
            if(container!= null) {
                for (int c = 0; c < container.size(); c++) {
                    if (container.get(c).equals(s)) {
                        total_matches++;
                        for (int i = 0; i < wordList.size(); i++) {

                            if (wordList.get(i).getWord().equals(s)) {
                                found = true;
                                wordList.get(i).increaseFrequency();
                            }
                        }
                        if (found == false) {
                            wordList.add(new Word(s, 1));
                        }
                    }
                }
            }

        }
        System.out.println("In " + fileName);
        System.out.println("Number of words Matched = " + wordList.size());
        System.out.println("Total matches = " + total_matches);
        boolean isPerfectMatch = true;
        for(int i =0; i < wordList.size() ; i++){
            System.out.println(wordList.get(i).getWord() + "     " + wordList.get(i).getFrequency());
            if(wordList.get(i).getFrequency() > 1){
                isPerfectMatch = false;
            }
        }
        if(isPerfectMatch == true && wordList.size()==total_matches && container.size()!=0){
            System.out.println("Query was a PERFECT MATCH!!!");
        }




    }
}
