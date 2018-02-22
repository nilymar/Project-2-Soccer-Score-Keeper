package com.example.android.scorekeeper1;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private int scoreTeamA = 0; //storing the score for team A
    private int scoreTeamB = 0; //storing the score for team B
    private int foulsTeamA = 0; //storing the number of fouls for team A
    private int foulsTeamB = 0; //storing the number of fouls for team B
    private int yellowTeamA = 0; //storing the number of yellow cards for team A
    private int yellowTeamB = 0; //storing the number of yellow cards for team B
    private int redTeamA = 0; //storing the number of red cards for team A
    private int redTeamB = 0; //storing the number of red cards for team B
    private String teamAName; //storing the name for team A
    private String teamBName; //storing the name for team B
    private SharedPreferences myPreferences; //variable for the preferences file location
    private SharedPreferences.Editor myEditor; //variable for the preferences file editor


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //when the app starts - the restore method is called so that if the user saved his Preferences they are shown on screen//
        restorePreferences();
    }

    /**
     * this method is called when the user exit the app
     */
    private void exit() {
        this.finish();
    }

    /**
     * this method is called when the user press keyDown to exit the app - it user the exit_dialog.xml layout
     **/
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            // only if the names of the teams are different from "team a" and "team b" and alert dialog will appear before exiting
            if ((teamAName.equals(getString(R.string.team_a))) && (teamBName.equals(getString(R.string.team_b)))) {
                exit();
                return true;
            } else { // creating the alert dialog only if the name of one of the teams is not the default
                LayoutInflater layoutInflater = LayoutInflater.from(this); // to inflate the prompt view from the current view
                View promptsView = layoutInflater.inflate(R.layout.exit_dialog, null); // the dialog view is set to the layout file exit_dialog.xml
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this); //creating a new alert dialog builder
                alertDialogBuilder.setView(promptsView); // setting  exit_dialog.xml to alertDialog builder
                final Button quitAndSave = (Button) promptsView.findViewById(R.id.quit_save); // finding the quit and save button
                final Button quitNoSave = (Button) promptsView.findViewById(R.id.quit_no_save); // finding the quit without saving Button
                final AlertDialog alertDialog = alertDialogBuilder.create(); // creating the alert dialog

                // setting the dialog window buttons - okButton for the saving of the user input and cancelButton for not saving
                quitAndSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //when the user click the button the names of the teams he created are saved before exit//
                        savePreferences();
                        exit();
                        // Dismiss the alert dialog
                        alertDialog.cancel();
                    }
                });
                quitNoSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //when the user click "Quit without saving" his preferences are deleted before exit//
                        clearSavedPreferences();
                        exit();
                        // Dismiss the alert dialog
                        alertDialog.cancel();
                    }
                });

                alertDialog.show();// showing it on screen
                return true;
            }
        } else return super.onKeyDown(keyCode, event);
    }

    /**
     * this is the method that restores the data that was saved when the user quit the app
     */
    public void restorePreferences() {
        myPreferences = this.getPreferences(Context.MODE_PRIVATE);
        // if there was a name saved for team a - restore it, otherwise use the default name
        if (myPreferences.contains("teamAName")) {
            teamAName = myPreferences.getString("teamAName", getString(R.string.team_a));
        } else teamAName = getString(R.string.team_a);
        // if there was a name saved for team b - restore it, otherwise use the default name
        if (myPreferences.contains("teamBName")) {
            teamBName = myPreferences.getString("teamBName", getString(R.string.team_a));
        } else teamBName = getString(R.string.team_b);
        // display the name of the teams on screen
        displayTeamAName();
        displayTeamBName();
    }

    /**
     * This method is called when the "Quit and save" button is clicked
     */
    public void savePreferences() {
        myPreferences = this.getPreferences(Context.MODE_PRIVATE);
        myEditor = myPreferences.edit();
        myEditor.clear().apply();
        // save the names of the teams in the preferences file
        myEditor.putString("teamAName", teamAName);
        myEditor.putString("teamBName", teamBName);
        // apply changes to the file
        myEditor.apply();
    }

    /**
     * This method is for erasing the data that was stored in the app preferences
     */
    public void clearSavedPreferences() {
        myPreferences = this.getPreferences(Context.MODE_PRIVATE);
        myEditor = myPreferences.edit();
        myEditor.clear().apply();
    }

    /**
     * Displays the score for Team A
     */
    public void displayForTeamA(int score) {
        TextView scoreView = findViewById(R.id.team_a_score);
        scoreView.setText(String.valueOf(score));
    }

    /**
     * Displays the fouls for Team A
     */
    public void displayFoulsForTeamA(int foul) {
        TextView foulView = findViewById(R.id.team_a_fouls);
        foulView.setText(String.valueOf(foul));
    }

    /**
     * Displays the yellow cards for Team A
     */
    public void displayYellowsTeamA(int yellow) {
        TextView yellowView = findViewById(R.id.team_a_yellows);
        yellowView.setText(String.valueOf(yellow));
    }

    /**
     * Displays the red cards for Team A
     */
    public void displayRedsTeamA(int red) {
        TextView redView = findViewById(R.id.team_a_reds);
        redView.setText(String.valueOf(red));
    }

    /**
     * add a goal to Team A
     */
    public void addGoalTeamA(View view) {
        scoreTeamA = scoreTeamA + 1;
        displayForTeamA(scoreTeamA);
    }

    /**
     * add  a foul to Team A
     */
    public void addFoulsTeamA(View view) {
        foulsTeamA = foulsTeamA + 1;
        displayFoulsForTeamA(foulsTeamA);
    }

    /**
     * add  yellow card to Team A
     */
    public void addYellowsTeamA(View view) {
        yellowTeamA = yellowTeamA + 1;
        displayYellowsTeamA(yellowTeamA);
    }

    /**
     * add a red card to Team A
     */
    public void addRedsTeamA(View view) {
        redTeamA = redTeamA + 1;
        displayRedsTeamA(redTeamA);
    }

    /**
     * Displays the score for Team B
     */
    public void displayForTeamB(int score) {
        TextView scoreView = findViewById(R.id.team_b_score);
        scoreView.setText(String.valueOf(score));
    }

    /**
     * Displays the fouls for Team B
     */
    public void displayFoulsForTeamB(int foul) {
        TextView foulView = findViewById(R.id.team_b_fouls);
        foulView.setText(String.valueOf(foul));
    }

    /**
     * Displays the yellow cards for Team B
     */
    public void displayYellowsTeamB(int yellow) {
        TextView yellowView = findViewById(R.id.team_b_yellows);
        yellowView.setText(String.valueOf(yellow));
    }

    /**
     * Displays the red cards for Team B
     */
    public void displayRedsTeamB(int red) {
        TextView redView = findViewById(R.id.team_b_reds);
        redView.setText(String.valueOf(red));
    }

    /**
     * add a goal to Team B
     */
    public void addGoalTeamB(View view) {
        scoreTeamB = scoreTeamB + 1;
        displayForTeamB(scoreTeamB);
    }

    /**
     * add a foul to Team B
     */
    public void addFoulsTeamB(View view) {
        foulsTeamB = foulsTeamB + 1;
        displayFoulsForTeamB(foulsTeamB);
    }

    /**
     * add a yellow card to Team B
     */
    public void addYellowsTeamB(View view) {
        yellowTeamB = yellowTeamB + 1;
        displayYellowsTeamB(yellowTeamB);
    }

    /**
     * add a red card to Team B
     */
    public void addRedsTeamB(View view) {
        redTeamB = redTeamB + 1;
        displayRedsTeamB(redTeamB);
    }

    /**
     * display the name of team A on screen
     */
    public void displayTeamAName() {
        TextView teamATextView = (TextView) findViewById(R.id.team_A_name);
        teamATextView.setText(teamAName);
    }

    /**
     * display the name of team B on screen
     */
    public void displayTeamBName() {
        TextView teamBTextView = (TextView) findViewById(R.id.team_B_name);
        teamBTextView.setText(teamBName);
    }

    /**
     * this method is for an alert dialog window to be prompt when the change_teamA_name button is pressed
     * I use an existing layout (change_team_name.xml) to create the alert dialog
     */
    public void changeTeamAName(View view) {
        LayoutInflater layoutInflater = LayoutInflater.from(this); // to inflate the prompt view from the current view
        View promptsView = layoutInflater.inflate(R.layout.change_team_name, null); // the dialog view is set to the layout file change_team_name.xml
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this); //creating a new alert dialog builder
        alertDialogBuilder.setView(promptsView); // setting  change_team_name.xml to alertDialog builder
        final EditText userInput = (EditText) promptsView.findViewById(R.id.user_input); // finding the EditText view for the user input
        final Button okButton = (Button) promptsView.findViewById(R.id.ok_button); // finding the ok button
        final Button cancelButton = (Button) promptsView.findViewById(R.id.cancel_button); // finding the cancel Button
        final AlertDialog alertDialog = alertDialogBuilder.create(); // creating the alert dialog

        // setting the dialog window buttons - okButton for the saving of the user input and cancelButton for not saving
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input;
                input = userInput.getText().toString();
                if (!input.isEmpty()) { // if the user put a new name
                    teamAName = input; // save the new name in the name variable
                    displayTeamAName(); // change the name shown on screen to the user input
                }
                // Dismiss the alert dialog
                alertDialog.cancel();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Dismiss the alert dialog
                alertDialog.cancel();
            }
        });

        alertDialog.show(); // showing it on screen
    }

    /**
     * this method is for an alert dialog window to be prompt when the change_teamB_name button is pressed
     * I use an existing layout (change_team_name.xml) to create the alert dialog
     */
    public void changeTeamBName(View view) {
        LayoutInflater layoutInflater = LayoutInflater.from(this); // to inflate the prompt view from the current view
        View promptsView = layoutInflater.inflate(R.layout.change_team_name, null); // the dialog view is set to the layout file change_team_name.xml
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this); //creating a new alert dialog builder
        alertDialogBuilder.setView(promptsView); // setting  change_team_name.xml to alertDialog builder
        final EditText userInput = (EditText) promptsView.findViewById(R.id.user_input); // finding the EditText view for the user input
        final Button okButton = (Button) promptsView.findViewById(R.id.ok_button); // finding the ok button
        final Button cancelButton = (Button) promptsView.findViewById(R.id.cancel_button); // finding the cancel Button
        final AlertDialog alertDialog = alertDialogBuilder.create(); // creating the alert dialog

        // setting the dialog window buttons - okButton for the saving of the user input and cancelButton for not saving
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input;
                input = userInput.getText().toString();
                if (!input.isEmpty()) { // if the user put a new name
                    teamBName = input; // save the new name in the name variable
                    displayTeamBName(); // change the name shown on screen to the user input
                }
                // Dismiss the alert dialog
                alertDialog.cancel();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Dismiss the alert dialog
                alertDialog.cancel();
            }
        });

        alertDialog.show(); // showing it on screen
    }

    /**
     * reset all data to zero and displays it on screen (does not reset the teams names)
     */
    public void resetTeamScores(View view) {
        scoreTeamA = 0;
        scoreTeamB = 0;
        foulsTeamA = 0;
        foulsTeamB = 0;
        yellowTeamA = 0;
        yellowTeamB = 0;
        redTeamA = 0;
        redTeamB = 0;
        displayForTeamA(scoreTeamA);
        displayForTeamB(scoreTeamB);
        displayFoulsForTeamA(foulsTeamA);
        displayFoulsForTeamB(foulsTeamB);
        displayYellowsTeamA(yellowTeamA);
        displayYellowsTeamB(yellowTeamB);
        displayRedsTeamA(redTeamA);
        displayRedsTeamB(redTeamB);
    }

    /*this method saves the data in the app - scores, fouls, red cards and yellow cards for both teams*/
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("teamAName", teamAName);
        outState.putString("teamBName", teamBName);
        outState.putInt("scoreTeamA", scoreTeamA);
        outState.putInt("scoreTeamB", scoreTeamB);
        outState.putInt("foulsTeamA", foulsTeamA);
        outState.putInt("foulsTeamB", foulsTeamB);
        outState.putInt("yellowTeamA", yellowTeamA);
        outState.putInt("yellowTeamB", yellowTeamB);
        outState.putInt("redTeamA", redTeamA);
        outState.putInt("redTeamB", redTeamB);
    }

    /**
     * this method will restore all the data saves in the previous method, when rotating the device
     **/
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        teamAName = savedInstanceState.getString("teamAName");
        displayTeamAName();
        teamBName = savedInstanceState.getString("teamBName");
        displayTeamBName();
        scoreTeamA = savedInstanceState.getInt("scoreTeamA");
        displayForTeamA(scoreTeamA);
        scoreTeamB = savedInstanceState.getInt("scoreTeamB");
        displayForTeamB(scoreTeamB);
        foulsTeamA = savedInstanceState.getInt("foulsTeamA");
        displayFoulsForTeamA(foulsTeamA);
        foulsTeamB = savedInstanceState.getInt("foulsTeamB");
        displayFoulsForTeamB(foulsTeamB);
        yellowTeamA = savedInstanceState.getInt("yellowTeamA");
        displayYellowsTeamA(yellowTeamA);
        yellowTeamB = savedInstanceState.getInt("yellowTeamB");
        displayYellowsTeamB(yellowTeamB);
        redTeamA = savedInstanceState.getInt("redTeamA");
        displayRedsTeamA(redTeamA);
        redTeamB = savedInstanceState.getInt("redTeamB");
        displayRedsTeamB(redTeamB);
    }
}

