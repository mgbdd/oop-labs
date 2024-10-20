package commands;

import main.ExecutionContext;

public class Comment implements Command{
    @Override
    public void execute(ExecutionContext context, String[] parts) {
        for (int i = 1; i < parts.length; i++) {
            System.out.print(parts[i] + " ");
        }
        System.out.println();
    }
}
