package View;

import View.Commands.Command;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by bnorbert on 06.11.2016.
 * bnorbertcristian@gmail.com
 */
public class TextMenu {

    private Map<String,Command> commands;

    public TextMenu(){
        commands=new HashMap<>();
    }

    private void addCommand(Command c){
        commands.put(c.getKey(),c);
    }

    private void printMenu(){
        for(Command command : commands.values()){
            String line = String.format("%4s : %s",command.getKey(),command.getDescription());
            System.out.println(line);
        }
    }

    public void show(){
        Scanner scanner=new Scanner(System.in);
        while(true){
            printMenu();
            System.out.printf("Input your option: ");
            String key=scanner.nextLine();
            Command command=commands.get(key);
            if(command == null){
                System.out.println("Invalid Option");
                continue;
            }
            command.execute();
        }

    }
}
