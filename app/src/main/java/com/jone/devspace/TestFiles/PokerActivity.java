package com.jone.devspace.TestFiles;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.jone.devspace.R;
import com.json.basewebview.Utils.LogUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class PokerActivity extends AppCompatActivity {

    public TextView tvA;
    public TextView tvB;
    public TextView tvC;
    public TextView tvD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poker);
        tvA = findViewById(R.id.tv_a);
        tvB = findViewById(R.id.tv_b);
        tvC = findViewById(R.id.tv_c);
        tvD = findViewById(R.id.tv_d);
        findViewById(R.id.btn_xi).setOnClickListener(view -> main());

    }

    private LinkedList<String> poker = new LinkedList<>();
    private LinkedList<String> localPoker = new LinkedList<>();
    private List<String> a_Poker = new ArrayList<>();
    private List<String> b_Poker = new ArrayList<>();
    private List<String> c_Poker = new ArrayList<>();

    public void main() {
        if (poker.size() != 0) {
            poker.clear();
        }
        if (a_Poker.size() != 0) {
            a_Poker.clear();
        }
        if (b_Poker.size() != 0) {
            b_Poker.clear();
        }
        if (c_Poker.size() != 0) {
            c_Poker.clear();
        }
        if (localPoker.size() == 0) {
            String[] huase = {"♥", "♠", "♦", "♣"};
            //一副扑克牌的牌面是1-10，J,Q,K13种情况
            String[] cardsNumber = {
                    "A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"
            };
            String type = null;
            int one = (int) ((Math.random()) * 12);
            int two = getNoRepeatIndex(one);
            for (int i = 0; i < huase.length; i++) {
                type = huase[i];
                for (int j = 0; j < cardsNumber.length; j++) {
                    if (j == one) {
                        poker.add("大王");
                        one = 100;
                    }
                    if (j == two) {
                        poker.add("小王");
                        two = 100;
                    }
                    poker.add(type + ":" + cardsNumber[j]);
                }
            }
        } else {
            poker = localPoker;
        }

        int size = 54;
        int currentIndex = 0;
        String currentPoke = null;
        for (int i = 0; i < 51; i++) {
            currentIndex = (int) ((Math.random()) * (size - 1));
            currentPoke = poker.get(currentIndex);
            switch (i % 3) {
                case 0:
                    a_Poker.add(currentPoke);
                    break;
                case 1:
                    b_Poker.add(currentPoke);
                    break;
                case 2:
                    c_Poker.add(currentPoke);
                    break;
                default:
            }
            size--;
            poker.remove(currentIndex);
        }
        if (localPoker.size() != 0) {
            localPoker.clear();
            localPoker.addAll(poker);
            localPoker.addAll(a_Poker);
            localPoker.addAll(b_Poker);
            localPoker.addAll(c_Poker);
        }
        sortList(a_Poker, tvA);
        sortList(b_Poker, tvB);
        sortList(c_Poker, tvC);
        sortList(poker, tvD);
    }

    private int getNoRepeatIndex(int num) {
        int current = (int) ((Math.random()) * 12);
        if (num == current) {
            return getNoRepeatIndex(num);
        }
        return current;
    }

    public void sortList(List<String> mList, TextView tv) {
        Collections.sort(mList, new SortByDigital());
        String result = "";
        for (String s : mList) {
            result = result + s + " ";
        }
        tv.setText(result);
    }

    class SortByDigital implements Comparator {
        String s1;
        String s2;
        int i1;
        int i2;

        @Override
        public int compare(Object o1, Object o2) {
            s1 = (String) o1;
            s2 = (String) o2;
            i1 = s1.indexOf(":");
            i2 = s2.indexOf(":");
            if (i1 == -1 && i2 == -1) {
                if (s1.contains("大")) {
                    return 1;
                }
            } else if (i1 == -1) {
                return 1;
            } else if (i2 == -1) {
                return -1;
            } else {
                s1 = s1.substring(i1 + 1);
                s2 = s2.substring(i2 + 1);
                if (toDigital(s1) - toDigital(s2) > 0) {
                    return 1;
                }
            }
            return -1;
        }
    }

    private int toDigital(String s) {
        switch (s) {
            case "A":
                return 14;
            case "2":
                return 15;
            case "J":
                return 11;
            case "Q":
                return 12;
            case "K":
                return 13;
            default:
                return Integer.parseInt(s);

        }
    }

}
