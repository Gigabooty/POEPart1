/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poe.part.pkg1;

/**
 *
 * @author Student
 */
class Login {
    


    private String username;
    private String password;
    private String cellPhoneNumber;
    private String firstName;
    private String lastName;

    
    public Login() {
    }

   

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCellPhoneNumber() {
        return cellPhoneNumber;
    }

    public void setCellPhoneNumber(String cellPhoneNumber) {
        this.cellPhoneNumber = cellPhoneNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

   
    public boolean checkUserName(String username) {
        if (username.contains("_") && username.length() <= 5) {
            return true;
        } else {
            return false;
        }
    }

   
    public boolean checkPasswordComplexity(String password) {
        boolean hasCapital = false;
        boolean hasNumber = false;
        boolean hasSpecialChar = false;

        
        for (int i = 0; i < password.length(); i++) {
            char currentChar = password.charAt(i);

            if (Character.isUpperCase(currentChar)) {
                hasCapital = true;
            }
            if (Character.isDigit(currentChar)) {
                hasNumber = true;
            }
            if (!Character.isLetterOrDigit(currentChar)) {
                hasSpecialChar = true;
            }
        }

        if (password.length() >= 8 && hasCapital && hasNumber && hasSpecialChar) {
            return true;
        } else {
            return false;
        }
    }

   
    public boolean checkCellPhoneNumber(String cellPhoneNumber) {
        
        String pattern = "^\\+27\\d{7,9}$";

        if (cellPhoneNumber.length() <= 12 && Pattern.matches(pattern, cellPhoneNumber)) {
            return true;
        } else {
            return false;
        }
    }

    
    public String registerUser(String username, String password, String cellPhoneNumber,
            String firstName, String lastName) {

        if (!checkUserName(username)) {
            return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";
        }

        if (!checkPasswordComplexity(password)) {
            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }

        if (!checkCellPhoneNumber(cellPhoneNumber)) {
            return "Cell phone number is incorrectly formatted or does not contain an international code; please correct the number and try again.";
        }

        // If we get here, everything passed, so we save the details
        this.username = username;
        this.password = password;
        this.cellPhoneNumber = cellPhoneNumber;
        this.firstName = firstName;
        this.lastName = lastName;

        return "Username successfully captured. Password successfully captured. Cell number successfully captured.";
    }

    
    public boolean loginUser(String enteredUsername, String enteredPassword) {
        if (enteredUsername.equals(this.username) && enteredPassword.equals(this.password)) {
            return true;
        } else {
            return false;
        }
    }

    
    public String returnLoginStatus(String enteredUsername, String enteredPassword) {
        if (loginUser(enteredUsername, enteredPassword)) {
            return "Welcome " + firstName + ", " + lastName + " it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }
}

