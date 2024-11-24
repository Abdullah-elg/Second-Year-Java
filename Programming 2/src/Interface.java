import java.io.*;

// Class interface to read input file, process user commands, and interact with the dictionary
public class Interface {
    private static BSTDictionary dictionary = new BSTDictionary();

    /**
     * Main method to read input file, process user commands, and interact with the dictionary
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (args.length != 1) { // if the input file is not provided, exit the program
            System.out.println("Usage: java Interface <input file>");
            System.exit(1);
        }
        String inputFile = args[0]; // get the input file
        readFile(inputFile); // read the input file
        userCommands(); // process user commands
    }

    /**
     * Reads the input file and inserts records into the dictionary
     * @param inputFile the input file to read
     */
    private static void readFile(String inputFile) {
        try (BufferedReader file = new BufferedReader(new FileReader(inputFile))) { // read the input file
            String label = file.readLine(); // read the first line
            while (label != null) { // read until the end of the file
                String line = file.readLine(); // read the next line
                int type; // type of the record
                String data; // data of the record
                if (line == null) { // if the line is null, throw an exception if invalid input file
                    throw new Exception("Invalid input file");
                } else if (line.charAt(0) == '-') { // if the line starts with a '-', set the type to 3 and store the data without the '-'
                    type = 3;
                    data = line.substring(1);
                } else if (line.charAt(0) == '+') {  // if the line starts with a '+', set the type to 4 and store the data without the '+'
                    type = 4;
                    data = line.substring(1);
                } else if (line.charAt(0) == '*') { // if the line starts with a '*', set the type to 5 and store the data without the '*'
                    type = 5;
                    data = line.substring(1);
                } else if (line.charAt(0) == '/') { // if the line starts with a '/', set the type to 2 and  store the data without the '/'
                    type = 2;
                    data = line.substring(1);
                } else {
                    String[] stringArr = line.split("\\."); // split the line by '.'
                    if (stringArr.length == 2) { // if the length is 2, set the type based on the extension
                        String y = stringArr[1]; 
                        if (y.equals("gif")) { // if the extension is 'gif', set the type to 7
                            type = 7;
                        } else if (y.equals("jpg")) { // if the extension is 'jpg', set the type to 6
                            type = 6;
                        } else if (y.equals("html")) { // if the extension is 'html', set the type to 8
                            type = 8;
                        } else { // otherwise, set the type to 1
                            type = 1;
                        }
                    } else { // if the length is not 2, set the type to 1
                        type = 1;
                    }
                    data = line; // store the data from the line
                }
                Key k = new Key(label.toLowerCase(), type); // create a new key with the label and type
                Record r = new Record(k, data); // create a new record with the key and data
                dictionary.put(r); // insert the record into the dictionary
                label = file.readLine(); // read the next label
            }
        } catch (Exception e) { // catch any exceptions and print the error message and exit the program
            System.out.println(e.getMessage()); 
            System.exit(1); 
        }
    }

    /**
     * Processes user commands and interacts with the dictionary
     */
    private static void userCommands() {
        StringReader keyboard = new StringReader(); // create a new StringReader object
        String line = keyboard.read("Enter next command: "); // read the first command
        while (!line.equals("exit")) { // read until the user enters 'exit'
            String[] words = line.split(" "); // split the line by spaces
            String command = words[0]; // get the command
            String s, w = "";
            Key k;
            if (words.length > 1) { // if there are more than 1 words, set the second word to w
                w = words[1].toLowerCase();
            }
            if (command.equals("define")) { // if the command is 'define', set the type to 1 and process the command with the key and message
                k = new Key(w, 1);
                s = String.format("The word %s is not in the dictionary", w);
                processCommand("string", k, s);
            } else if (command.equals("translate")) { // if the command is 'translate', set the type to 2 and process the command with the key and message
                k = new Key(w, 2);
                s = String.format("There is no definition for the word %s", w);
                processCommand("string", k, s);
            } else if (command.equals("sound")) { // if the command is 'sound', set the type to 3 and process the command with the key and message
                k = new Key(w, 3);
                s = String.format("There is no sound file for %s", w);
                processCommand("sound", k, s);
            } else if (command.equals("play")) { // if the command is 'play', set the type to 4 and process the command with the key and message
                k = new Key(w, 4);
                s = String.format("There is no music file for %s", w);
                processCommand("sound", k, s);
            } else if (command.equals("say")) { // if the command is 'say', set the type to 5 and process the command with the key and message
                k = new Key(w, 5);
                s = String.format("There is no voice file for %s", w);
                processCommand("sound", k, s);
            } else if (command.equals("show")) { // if the command is 'show', set the type to 6 and process the command with the key and message
                k = new Key(w, 6);
                s = String.format("There is no image file for %s", w);
                processCommand("picture", k, s);
            } else if (command.equals("animate")) { // if the command is 'animate', set the type to 7 and process the command with the key and message
                k = new Key(w, 7);
                s = String.format("There is no animated image file for %s", w);
                processCommand("picture", k, s);
            } else if (command.equals("browse")) { // if the command is 'browse', set the type to 8 and process the command with the key and message
                k = new Key(w, 8);
                s = String.format("There is no webpage called %s", w);
                processCommand("webpage", k, s);
            } else if (command.equals("delete")) { // if the command is 'delete', remove the record with the key from the dictionary
                k = new Key(w, Integer.parseInt(words[2]));
                try { // try to remove the record with the key
                    dictionary.remove(k);
                } catch (DictionaryException e) { // catch any exceptions and print the error message
                    System.out.printf("No record in the ordered dictionary has key (%s,%d)\n", w, Integer.parseInt(words[2]));
                }
            } else if (command.equals("add")) { // if the command is 'add', add the record with the record to the dictionary
                k = new Key(w, Integer.parseInt(words[2]));
                String substr = line.substring(line.indexOf(words[3])); // get the data from the line
                Record d = new Record(k, substr); // create a new record with the key and data
                try { // try to add the record to the dictionary
                    dictionary.put(d);
                } catch (DictionaryException e) { // catch any exceptions and print the error message
                    System.out.printf("A record with the given key (%s,%d) is already in the ordered dictionary\n", w, Integer.parseInt(words[2]));
                }
            } else if (command.equals("list")) { // if the command is 'list', list all the records in the dictionary
                Record temp = dictionary.smallest(); // get the smallest record
                int count = 0; // count of the records
                while (temp != null) { // loop through the records
                    if (temp.getKey().getLabel().startsWith(w)) { // if the label starts with the prefix, print the label
                        if (count > 0) { // if the count is greater than 0, print a comma
                            System.out.print(", ");
                        }
                        System.out.print(temp.getKey().getLabel()); // print the label
                        count++; 
                    }
                    temp = dictionary.successor(temp.getKey()); // get the successor of the record to continue the loop
                }
                System.out.println();
            } else if (command.equals("first")) { // if the command is 'first', get the smallest record in the dictionary
                Record small = dictionary.smallest(); // get the smallest record
                if (small != null) { // if the record is not null, print the record details
                    System.out.println(small.getKey().getLabel() + "," + small.getKey().getType() + "," + small.getDataItem());
                } else {
                    System.out.println("The ordered dictionary is empty");
                }
            } else if (command.equals("last")) { // if the command is 'last', get the largest record in the dictionary
                Record large = dictionary.largest(); // get the largest record
                if (large != null) { // if the record is not null, print the record details
                    System.out.println(large.getKey().getLabel() + "," + large.getKey().getType() + "," + large.getDataItem());
                } else {
                    System.out.println("The ordered dictionary is empty");
                }
            } else { // if the command is invalid, print "Invalid command"
                System.out.println("Invalid command");
            }
            line = keyboard.read("Enter next command: "); // read the next command
        }
    }

    /**
     * Processes the command based on the file type and key
     * @param fileType the type of the file
     * @param k the key of the record
     * @param s the message to print if the record does not exist
     */
    private static void processCommand(String fileType, Key k, String s) {
        try {
            if (fileType.equals("string")) { // if the file type is 'string', get the record with the key and print the data
                Record d = dictionary.get(k); // get the record with the key
                if (d != null) { // if the record exists, print the data, otherwise print the message
                    System.out.println(d.getDataItem());
                } else {
                    System.out.println(s);
                }
            } else if (fileType.equals("sound")) { // if the file type is 'sound', get the record with the key and play the sound
                Record d = dictionary.get(k); // get the record with the key
                if (d != null) { // if the record exists, play the sound, otherwise print the message
                    SoundPlayer player = new SoundPlayer();
                    player.play(d.getDataItem());
                } else {
                    System.out.println(s);
                }
            } else if (fileType.equals("picture")) { // if the file type is 'picture', get the record with the key and show the picture
                Record d = dictionary.get(k); // get the record with the key
                if (d != null) { // if the record exists, show the picture, otherwise print the message
                    PictureViewer viewer = new PictureViewer();
                    viewer.show(d.getDataItem());
                } else {
                    System.out.println(s);
                }
            } else if (fileType.equals("webpage")) { // if the file type is 'webpage', get the record with the key and show the webpage
                Record d = dictionary.get(k); // get the record with the key
                if (d != null) { // if the record exists, show the webpage, otherwise print the message
                    ShowHTML browser = new ShowHTML();
                    browser.show(d.getDataItem());
                } else {
                    System.out.println(s);
                }
            }
        } catch (MultimediaException e) { // catch any exceptions and print the error message
            System.out.println(e.getMessage());
        }
    }
}
