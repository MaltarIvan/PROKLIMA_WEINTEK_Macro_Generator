package generators;

import GUI.MainForm;
import entities.Signal;
import entities.States;
import exceptions.ConfigurationNotDoneException;
import utilities.ConfigMain;
import utilities.ConfigSignals;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public final class HMIInit {
    private static final String MACRO_NAME = "HMI_init.txt";
    private static final String VERSION_TAG = "// Version: ";
    private static final String OPEN_MACRO_COMMAND = "macro_command main()";
    private static final String END_MACRO_COMMAND = "end macro_command";
    private static final String DIGITAL_STATES_STRINGS_START_DEC = "short DIGITAL_STATES_STRINGS_START = ";
    private static final String DIGITAL_STATES_STRING_START = "DIGITAL_STATES_STRINGS_START";
    private static final String COMMENT = "//";
    private static final String INDEX = "index = ";
    private static final String COUNT = "count = ";

    private HMIInit() {}

    public static File generateMacro(ConfigMain configMain, ConfigSignals configSignals, String path, MainForm mainForm) throws ConfigurationNotDoneException, IOException {
        path += "\\" + MACRO_NAME;
        ArrayList<Signal> signals = new ArrayList<>();
        File file = new File(path);
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream));

        if (!configMain.isConfigurated() || !configSignals.isConfigurated()) {
            throw new ConfigurationNotDoneException();
        }

        signals.addAll(configSignals.getMainDigital());
        signals.addAll(configSignals.getAdvancedDigital());
        signals.addAll(configSignals.getAdvancedInputs());
        signals.addAll(configSignals.getAdvancedFan());
        signals.addAll(configSignals.getAdvancedTemp());

        Collections.sort(signals, new Comparator<Signal>() {

            @Override
            public int compare(Signal s1, Signal s2) {
                return s1.getStates().getStateStringIndex() - s2.getStates().getStateStringIndex();
            }
        });

        ArrayList<States> allStates = new ArrayList<>();
        for (Signal signal : signals) {
            if (!allStates.contains(signal.getStates())) {
                allStates.add(signal.getStates());
            }
        }

        bufferedWriter.write(VERSION_TAG + configSignals.getVersion());
        bufferedWriter.newLine();
        bufferedWriter.write(OPEN_MACRO_COMMAND);
        bufferedWriter.newLine();
        bufferedWriter.newLine();
        bufferedWriter.write(DIGITAL_STATES_STRINGS_START_DEC + configMain.getDigitalStateStringStart());
        bufferedWriter.newLine();
        bufferedWriter.newLine();

        String stringVar;
        int stringVarCount = 0;
        int statesStringCount = 0;
        ArrayList<Integer> stringVarSizes = new ArrayList<>();
        for (int lanInd = 0; lanInd < configMain.getLanguageCount(); lanInd++) {
            statesStringCount = 0;
            bufferedWriter.write("// Language: " + lanInd);
            bufferedWriter.newLine();
            for (States states : allStates) {
                String[] statesStr = states.getStatesStr().get(lanInd);
                for (int i = 0; i < statesStr.length; i++) {
                    statesStringCount++;
                    stringVar = "char string" + (stringVarCount + 1) + "[" + statesStr[i].length() + "] = \"" +statesStr[i] + "\"";
                    stringVarSizes.add(statesStr[i].length());
                    if (i == 0) {
                        stringVar += COMMENT + " ";
                        for (Signal signal : states.getSignals()) {
                            stringVar += signal.getSignalName() + ", ";
                        }
                        stringVar = stringVar.substring(0, stringVar.length() - 2);
                        stringVar += " : " + INDEX + states.getStateStringIndex() + ", " + COUNT  + statesStr.length;
                    }
                    bufferedWriter.write(stringVar);
                    bufferedWriter.newLine();
                    stringVarCount++;
                }
            }
            bufferedWriter.newLine();
        }

        String stringSet = "";
        for (int lanInd = 0; lanInd < configMain.getLanguageCount(); lanInd++) {
            for (int i = 0; i < statesStringCount; i++) {
                stringSet = "StringSet(string" + (lanInd * statesStringCount + i + 1)
                        + "[0], \"HMI\", RW, " + DIGITAL_STATES_STRING_START + " + " + (200 * lanInd + i)
                        + " * 10, " + stringVarSizes.get(lanInd * statesStringCount + i)
                        + ")";
                bufferedWriter.write(stringSet);
                bufferedWriter.newLine();
            }
        }

        bufferedWriter.newLine();
        bufferedWriter.newLine();
        bufferedWriter.write(END_MACRO_COMMAND);

        bufferedWriter.close();
        mainForm.getLogArea().append("HMI_init macro done!\n");

        return file;
    }
}
