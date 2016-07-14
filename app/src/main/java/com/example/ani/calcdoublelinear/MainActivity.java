package com.example.ani.calcdoublelinear;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;

public class MainActivity extends AppCompatActivity {
    Button zero,one,two,three,four,five,six,seven,eight,nine,plus,minus,mul,div,eq,dot,ac;
    TextView editText1,editText2;
    String a="",po="";
    double result=0.0;
    boolean test=false,op=false,dec=false,eqpressed=false,help=true;
    final org.nfunk.jep.JEP myParser = new org.nfunk.jep.JEP();
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar= (Toolbar) findViewById(R.id.app_bar);

        setSupportActionBar(toolbar);

        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        myParser.addStandardFunctions();
        myParser.addStandardConstants();

        zero= (Button) findViewById(R.id.zero);
        one= (Button) findViewById(R.id.one);
        two= (Button) findViewById(R.id.two);
        three= (Button) findViewById(R.id.three);
        four= (Button) findViewById(R.id.four);
        five= (Button) findViewById(R.id.five);
        six= (Button) findViewById(R.id.six);
        seven= (Button) findViewById(R.id.seven);
        eight= (Button) findViewById(R.id.eight);
        nine= (Button) findViewById(R.id.nine);
        plus= (Button) findViewById(R.id.plus);
        minus= (Button) findViewById(R.id.minus);
        mul= (Button) findViewById(R.id.mul);
        div= (Button) findViewById(R.id.div);
        dot= (Button) findViewById(R.id.dot);
        eq= (Button) findViewById(R.id.eq);
        ac= (Button) findViewById(R.id.ac);
        editText1= (TextView) findViewById(R.id.editText1);
        editText2= (TextView) findViewById(R.id.editText2);

            dot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        if(dec==false) {
                            a = a + ".";
                            editText1.setText(String.valueOf(a));
                            dec=true;
                            if(eqpressed==true) {
                                ac.setText("DEL");
                                clear();
                                eqpressed = false;
                            }
                        }
                }
            });

            plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    a = a + "+";
                    editText1.setText(a);
                    dec=false;
                    op=true;
                    eqpressed=false;
                }
            });

            minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    a=a+"-";
                    editText1.setText(a);
                    dec=false;
                    op=true;
                    eqpressed=false;
                }
            });

            mul.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(op==false) {
                        a = a + "*";
                        editText1.setText(a);
                        dec = false;
                        op = true;
                        eqpressed=false;
                    }
                }
            });

            div.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(op==false) {
                        a = a + "/";
                        editText1.setText(a);
                        dec = false;
                        op = true;
                        eqpressed=false;
                    }
                }
            });

            eq.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(a.length()!=0) {
                        a = String.valueOf(result);
                        editText1.setText(a);
                        ac.setText("CLR");
                        dec = false;
                        eqpressed=true;
                    }
                }
            });

            ac.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(ac.getText().equals("CLR")) {
                        clear();
                    }
                    else {
                        if(a.length()!=0) {
                            a = method(a);
                            editText1.setText(String.valueOf(a));
                            myParser.parseExpression(a);
                            result = (float) myParser.getValue();
                            editText2.setText(String.valueOf(result));
                        }
                        else {
                            a = "";
                            editText1.setText("0");
                            editText2.setText(String.valueOf("Result"));
                        }
                    }
                }
            });
    }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.app_menu, menu);
            setoptionsmenu(menu);
            return super.onCreateOptionsMenu(menu);
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {

            super.onOptionsItemSelected(item);

            switch (item.getItemId()) {
                case R.id.item1:
                    Toast.makeText(getBaseContext(), "Work in progress for Settings :P", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.item2:
              //      Toast.makeText(getBaseContext(), "Work in progress for Help :P", Toast.LENGTH_SHORT).show();
                    help=false;
                    invalidateOptionsMenu();
                    help help1= new help();
                    FragmentTransaction t=getFragmentManager().beginTransaction();
                    t.add(R.id.mainlinearlayout,help1);
                    t.addToBackStack(null);
                    t.commit();
                    break;
            }
            return true;
        }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        Log.e("ani","calling onsaveinstancestate");

        outState.putString("text1", a);
        outState.putBoolean("opflag", op);
        outState.putBoolean("eqpressedflag", eqpressed);
        outState.putBoolean("decflag", dec);
        outState.putBoolean("help",help);

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        a=savedInstanceState.getString("text1");

        op=savedInstanceState.getBoolean("opflag");
        eqpressed=savedInstanceState.getBoolean("eqpressedflag");
        dec=savedInstanceState.getBoolean("decflag");
        help=savedInstanceState.getBoolean("help");

        editText1.setText(String.valueOf(a));
        myParser.parseExpression(a);
        result = (float) myParser.getValue();
        editText2.setText(String.valueOf(result));

        if(help==false)
        {
            help help=new help();
            FragmentTransaction t=getFragmentManager().beginTransaction();
            t.add(R.id.mainlinearlayout,help);
            t.addToBackStack(null);
            t.commit();
        }
    }

    public void buttonclick(View view){
        if(eqpressed==true) {
            ac.setText("DEL");
            clear();
            eqpressed=false;
        }
        switch(view.getId()){
            case R.id.zero:a = a + "0";
                number(a);
                break;
            case R.id.one:a = a + "1";
                number(a);
                break;
            case R.id.two:a = a + "2";
                number(a);
                break;
            case R.id.three:a = a + "3";
                number(a);
                break;
            case R.id.four:a = a + "4";
                number(a);
                break;
            case R.id.five:a = a + "5";
                number(a);
                break;
            case R.id.six:a = a + "6";
                number(a);
                break;
            case R.id.seven:a = a + "7";
                number(a);
                break;
            case R.id.eight:a = a + "8";
                number(a);
                break;
            case R.id.nine:a = a + "9";
                number(a);
                break;
        }

    }

    public void number(String n){
        op=false;
        editText1.setText(String.valueOf(a));
            myParser.parseExpression(a);
            result = (float) myParser.getValue();
            editText2.setText(String.valueOf(result));
    }

    public String method(String str) {
        String tempstr=str.substring(str.length()-1,str.length());
        if(tempstr.equals(".")){dec=false;}
        else
        if(tempstr.equals("+")||tempstr.equals("-")||tempstr.equals("*")||tempstr.equals("/")){op=false;}
        if (str.length() > 0) {
            str = str.substring(0, str.length()-1);
        }
        return str;
    }

    public void clear(){
        a = "";
        dec = false;
        editText1.setText("0");
        editText2.setText(String.valueOf("Result"));
        ac.setText("DEL");
        result=0.0;
        return;
    }

    @Override
    public void onBackPressed() {
        if(getFragmentManager().getBackStackEntryCount()==0)
            finish();
        else {
            getFragmentManager().popBackStack();
            help=true;
            invalidateOptionsMenu();
        }
    }

    public void setoptionsmenu(Menu menu){
        if (help==false){menu.findItem(R.id.item2).setVisible(false);}
        else
        {menu.findItem(R.id.item2).setVisible(true);}
    }
}
