package application;

import javax.swing.JOptionPane;

public abstract class BaseUserModal {
    protected String userName = JOptionPane.showInputDialog(null, "Enter name");

    public String getUserName() {
        return userName;
    }
}
