package ru.avtf.kitpo.kitpo_rgr_konovalov;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import ru.avtf.kitpo.kitpo_rgr_konovalov.cycle_list.CycleList;
import ru.avtf.kitpo.kitpo_rgr_konovalov.factory.UserFactory;
import ru.avtf.kitpo.kitpo_rgr_konovalov.types.users.UserType;

public class MainActivity extends AppCompatActivity {

    public UserFactory userFactory;
    public UserType userType;
    public CycleList cycleList;
    private final String FILE_NAME_DOUBLE = "double.txt";
    private final String FILE_NAME_POINT = "point.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userFactory = new UserFactory();
        ArrayList<String> typeNameList = userFactory.getTypeNameList();
        String[] types = new String[typeNameList.size()];
        for (int i = 0; i < typeNameList.size(); i++) {
            types[i] = typeNameList.get(i);
            Log.d("PRINT_TYPES", types[i]);
        }
        Spinner spinner = (Spinner) findViewById(R.id.factoryListItems);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item,
                        types);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("PRINT_TYPES_SELECT", "Type is selected " + parent.getSelectedItem().toString());
                userType = UserFactory.getBuilderByName(parent.getSelectedItem().toString());
                assert userType != null;
                cycleList = new CycleList(userType.getTypeComparator());
                setTextOnOutTextField();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        buttonInitialization();
    }

    /**
     * ?????????? ?????????????????????????? ????????????
     * ???????????????????? ???????????????? ?? ??????????????
     */
    private void buttonInitialization() {

        Button deleteByIdButton = (Button) findViewById(R.id.deleteByIdButton);
        Button insertByIdButton = (Button) findViewById(R.id.insertByIdButton);
        Button sortButton = (Button) findViewById(R.id.sortButton);
        Button insertButton = (Button) findViewById(R.id.insertButton);
        Button saveButton = (Button) findViewById(R.id.saveButton);
        Button loadButton = (Button) findViewById(R.id.loadButton);
        Button clearButton = (Button) findViewById(R.id.clearButton);

        //???????????????? ???????????????? ???????????? ???? id
        deleteByIdButton.setOnClickListener((view) -> {
            EditText deleteByIdField = (EditText) findViewById(R.id.deleteByIdField);
            if (deleteByIdField.getText().toString().equals("")) {
                Toast.makeText(getBaseContext(), "?????????????? ???????????? ?????? ????????????????!", Toast.LENGTH_LONG).show();
            } else {
                if (cycleList.getByIndex(Integer.parseInt(String.valueOf(deleteByIdField.getText()))) == null) {
                    Toast.makeText(getBaseContext(), "?????????????? ???????????????????? ???????????? ?????? ????????????????!", Toast.LENGTH_LONG).show();
                } else {
                    cycleList.remove(Integer.parseInt(String.valueOf(deleteByIdField.getText())));
                    setTextOnOutTextField();
                }
            }
        });

        //?????????????? ???????????????? ???????????? ???? id
        insertByIdButton.setOnClickListener((view) -> {
            EditText insertByIdField = (EditText) findViewById(R.id.insertByIdField);
            if (insertByIdField.getText().toString().equals("")) {
                Toast.makeText(getBaseContext(), "?????????????? ???????????? ?????? ??????????????!", Toast.LENGTH_LONG).show();
            } else {
                if (cycleList.getByIndex(Integer.parseInt(String.valueOf(insertByIdField.getText()))) == null) {
                    Toast.makeText(getBaseContext(), "?????????????? ???????????????????? ???????????? ?????? ??????????????!", Toast.LENGTH_LONG).show();
                } else {
                    cycleList.add(userType.create(), Integer.parseInt(String.valueOf(insertByIdField.getText())));
                    setTextOnOutTextField();
                }
            }
        });

        //???????????????????? ???????????????????????? ????????????
        sortButton.setOnClickListener((view) -> {
            cycleList.sort(userType.getTypeComparator());
            setTextOnOutTextField();
        });

        //?????????????? ???????????????? ???????????? ?? ??????????
        insertButton.setOnClickListener((view) -> {
            cycleList.add(userType.create());
            setTextOnOutTextField();

        });

        //????????????????????????
        saveButton.setOnClickListener((view) -> {
            BufferedWriter bufferedWriter = null;
            try {
                Log.d("MY_TAG", userType.typeName());
                if (userType.typeName().equals("Double")) {
                    bufferedWriter = new BufferedWriter(new OutputStreamWriter((openFileOutput(FILE_NAME_DOUBLE, MODE_PRIVATE))));
                } else {
                    bufferedWriter = new BufferedWriter(new OutputStreamWriter((openFileOutput(FILE_NAME_POINT, MODE_PRIVATE))));
                }
            } catch (FileNotFoundException e) {
                Toast.makeText(getBaseContext(), "???????????? ?????? ???????????? ??????????!", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
            try {
                bufferedWriter.write(userType.typeName() + "\n");
            } catch (IOException e) {
                Toast.makeText(getBaseContext(), "???????????? ?????? ???????????? ??????????!", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
            BufferedWriter finalBufferedWriter = bufferedWriter;
            try {
                cycleList.forEach(el -> {
                    try {
                        finalBufferedWriter.write(el.toString() + "\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                Toast.makeText(getBaseContext(), "???????????? ?????????????? ???????????????? ?? ????????!", Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                Toast.makeText(getBaseContext(), "???????????? ?????? ???????????? ??????????!", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
            try {
                bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        //????????????????????????????
        loadButton.setOnClickListener((view) -> {
            BufferedReader bufferedReader;
            try {
                Log.d("MY_TAG", userType.typeName());
                if (userType.typeName().equals("Double")) {
                    bufferedReader = new BufferedReader(new InputStreamReader((openFileInput(FILE_NAME_DOUBLE))));
                } else {
                    bufferedReader = new BufferedReader(new InputStreamReader((openFileInput(FILE_NAME_POINT))));
                }
            } catch (Exception ex) {
                Toast.makeText(getBaseContext(), "???????????? ?????? ???????????? ??????????!", Toast.LENGTH_LONG).show();
                return;
            }
            String line;
            try {
                line = bufferedReader.readLine();
                if (line == null) {
                    Toast.makeText(getBaseContext(), "???????????? ?????? ???????????? ??????????!", Toast.LENGTH_LONG).show();
                    return;
                }
                if (!userType.typeName().equals(line)) {
                    Toast.makeText(getBaseContext(), "???????????????????????? ???????????? ??????????!", Toast.LENGTH_LONG).show();
                    return;
                }
                cycleList = new CycleList(userType.getTypeComparator());

                while ((line = bufferedReader.readLine()) != null) {
                    try {
                        cycleList.add(userType.parseValue(line));
                    } catch (Exception ex) {
                        Toast.makeText(getBaseContext(), "???????????? ?????? ???????????? ??????????!", Toast.LENGTH_LONG).show();
                        return;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            setTextOnOutTextField();
        });

        //?????????????? ???????????? ?? ????????????
        clearButton.setOnClickListener((view) -> {
            cycleList = new CycleList(userType.getTypeComparator());
            setTextOnOutTextField();
        });
    }

    /**
     * ???????????????????? ???????????? ???? ????????
     */
    private void setTextOnOutTextField() {
        TextView outTextView = (TextView) findViewById(R.id.outTextView);
        outTextView.setText(cycleList.toString());
    }

}