package com.example.appbeta;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Commands {

    private static Commands commands;
    private static HashMap<String,Value> commandsMap;
    private static State state;
    private static HashMap<String,String> values;
    public static  SharedPreferences CMDS_PREFS ;
    public static SharedPreferences.Editor cmdsEditor ;
    private  Context context;

    //private constructor to keep only one instance in the app
    private Commands(Context context) {
        this.context = context;
        initializeStorage();
        init();
        initValues();
        state = state.getInstance(context);
    }
    public static Commands getInstance(Context context){
        if(commands==null){
            commands = new Commands(context);
        }
        return commands;
    }

    private void initializeStorage() {
        CMDS_PREFS = context.getSharedPreferences("cmds_saver", Context.MODE_PRIVATE);
        cmdsEditor = CMDS_PREFS.edit();
        //hado mchaw lclass dyalhom
        //STATE_PREFS = context.getSharedPreferences("states_saver", Context.MODE_PRIVATE);
        //stateEditor = STATE_PREFS.edit();
    }
    private void saveToStorage() {
        //rej3t hadi generic ch7al ma kano dles commandes
        for(Map.Entry<String,Value> entry :commandsMap.entrySet()){
            cmdsEditor.putString(String.valueOf(entry.getValue().getByteValue()), entry.getKey()).apply();
        }

        /*cmdsEditor.putString("1", "ouvre la porte" ).apply();
        cmdsEditor.putString("2", "ferme la porte" ).apply();
        cmdsEditor.putString("3", "ouvre la fenêtre").apply();
        cmdsEditor.putString("4", "ferme la fenêtre").apply();
        cmdsEditor.putString("5", "allume la lampe").apply();
        cmdsEditor.putString("6", "eteins la lampe").apply();*/
    }





    private void init() {
        commandsMap = new HashMap<String, Value>();
        commandsMap.put(CMDS_PREFS.getString("1", "ouvre la porte"), new Value((byte) 1, false));
        commandsMap.put(CMDS_PREFS.getString("2", "ferme la porte"), new Value((byte) 2, true));
        commandsMap.put(CMDS_PREFS.getString("3", "ouvre les fenêtres"), new Value((byte) 3, false));
        commandsMap.put(CMDS_PREFS.getString("4", "ferme les fenêtres"), new Value((byte) 4, true));
        commandsMap.put(CMDS_PREFS.getString("5", "allume la lampe"), new Value((byte) 5, false));
        commandsMap.put(CMDS_PREFS.getString("6", "éteins la lampe"), new Value((byte) 6, true));
    }
    private void initValues(){
        values = new HashMap<String,String>();
        values.put("ouvre la porte",CMDS_PREFS.getString("1", "ouvre la porte"));
        values.put("ferme la porte",CMDS_PREFS.getString("2", "ferme la porte"));
        values.put("ouvre les fenêtres",CMDS_PREFS.getString("3", "ouvre les fenêtres"));
        values.put("ferme les fenêtres",CMDS_PREFS.getString("4", "ferme les fenêtres"));
        values.put("allume la lampe",CMDS_PREFS.getString("5", "allume la lampe"));
        values.put("éteins la lampe",CMDS_PREFS.getString("6", "éteins la lampe"));
    }
    public String getValue(String key){
        return values.get(key);
    }

    public void setCommand(String oldCommande, String newCommande) {
        if (commandsMap.containsKey(oldCommande)){
            Value value= commandsMap.get(oldCommande);
            commandsMap.put(newCommande, value);
            commandsMap.remove(oldCommande);
            //hadi drt mor makatbdl chi commande kitsejjlo
            saveToStorage();
            initValues();
        }
    }
    public Byte getCommande(String commande){
        Value value = commandsMap.get(commande);
        if(value != null && (value.getState() == state.getState(value.getByteValue()))){
            state.setState(value.getByteValue(), !value.getState());
            return value.getByteValue();
        }
        else{
            Log.d("valaue and state",value+" "+state.getState(value.getByteValue()));
            return null;
        }
    }
    public Map<String,Value> getAllCommands(){
        return commandsMap;
    }




    /* ==== States Section ====


    public void saveDoorState(boolean state) {

        stateEditor.putBoolean("door_state", state);
        stateEditor.apply();


    }

    public boolean getDoorState() {

        return STATE_PREFS.getBoolean("door_state", State.doorOpen);
    }


    public void saveWindowState(boolean state) {

        stateEditor.putBoolean("window_state", state);
        stateEditor.apply();
    }

    public boolean getWindowState() {

        return STATE_PREFS.getBoolean("window_state", State.windowsOpen);
    }

    public void saveLightState(boolean state) {

        stateEditor.putBoolean("light_state", state);
        stateEditor.apply();
    }

    public boolean getLightState() {

        return STATE_PREFS.getBoolean("light_state", State.lightsOn);
    }

     */

    /* ==== Cmds Section ==== */

    public void resetCommands () {

        cmdsEditor.putString("1", "ouvre la porte").apply();
        cmdsEditor.putString("2", "ferme la porte").apply();
        cmdsEditor.putString("3", "ouvre les fenêtres").apply();
        cmdsEditor.putString("4", "ferme les fenêtres").apply();
        cmdsEditor.putString("5", "allume la lampe").apply();
        cmdsEditor.putString("6", "éteins la lampe").apply();
        init();
        initValues();
    }

    /*public void updateCmd(String key, String value) {

        cmdsEditor.putString(key, value).apply();
    }

     */







}