package com.itheima;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import javax.print.DocFlavor.STRING;
import javax.sound.sampled.SourceDataLine;

public class ATM {
    private ArrayList<Account> accounts = new ArrayList<>();
    Scanner sc = new Scanner(System.in, "GBK");
    private Account loginAcc;

    /** 欢迎界面 **/
    public void Start() {
        while (true) {
            System.out.println("===欢迎使用ATM系统===");
            System.out.println("1、用户登录");
            System.out.println("2、用户开户");
            System.out.println("请选择：");
            int command = sc.nextInt();
            switch (command) {
                case 1:
                    login();
                    // 用户登录
                    break;
                case 2:
                    createAccount();
                    // 用户开户
                    break;
                default:
                    System.out.println("没有该操作");
            }
        }
    }

    /** 完成用户登录 */
    private void login() {
        System.out.println("==系统登录==");
        if (accounts.size() == 0) {
            System.out.println("当前系统没有任何账户");
            return;
        }
        while (true) {
            System.out.println("请输入您的卡号");
            String cardId = sc.next();
            Account acc = getAccountBycardId(cardId);
            if (acc == null) {
                System.out.println("输入的卡号不存在");
            } else {
                System.out.println("请输入密码");
                String passWord = sc.next();
                if (acc.getPassword().equals(passWord)) {
                    System.out.println("恭喜您" + acc.getUserName() + "成功登录");
                    loginAcc = acc;
                    // 展示登录之后的操作界面

                    showUserCommand();
                    return; // 退出当前login方法

                } else {
                    System.out.println("输入的密码不正确");
                }
            }
        }
    }

    private void showUserCommand() {
        while (true) {
            System.out.println(loginAcc.getUserName() + "====您可以选择如下功能进行账户的处理====");
            System.out.println("1. 查询账户");
            System.out.println("2. 存款");
            System.out.println("3. 取款");
            System.out.println("4. 转账");
            System.out.println("5. 密码修改");
            System.out.println("6. 退出");
            System.out.println("7. 注销当前账户");
            int command = sc.nextInt();
            switch (command) {
                case 1:
                    // 查询账户
                    showLoginAccount();
                    break;

                case 2:
                    // 存款
                    depositMoney();
                    break;

                case 3:
                    // 取款
                    drawMoney();
                    break;

                case 4:
                    // 转账
                    break;

                case 5:
                    // 密码修改

                    break;

                case 6:
                    System.out.println(loginAcc.getUserName() + "已经成功退出");
                    // 退出

                    return; // 跳出当前方法

                case 7:
                    // 注销账户

                    break;
                default:
                    System.out.println("输入的命令不存在");
            }
        }
    }

    private void drawMoney() {
        System.out.println("==取款操作==");
        if (loginAcc.getMoney() < 100) {
            System.out.println("账户余额不足100元，不允许取款");
            return;
        }
        while (true) {
            System.out.println("请您输入取款金额:");
            double money = sc.nextDouble();
            if (loginAcc.getMoney() > money) {
                if (money > loginAcc.getLimit()) {
                    System.out.println("取款金额超过了限额，不允许！");
                } else {
                    loginAcc.setMoney(loginAcc.getMoney() - money);
                    break;
                }
            } else {
                System.out.println("余额不足" + "你的余额是" + loginAcc.getMoney());
            }
        }
    }

    private void depositMoney() {
        System.out.println("==存钱操作==");
        System.out.println("请您输入存款金额:");
        double money = sc.nextDouble();
        loginAcc.setMoney(loginAcc.getMoney() + money);
        System.out.println("恭喜您存钱成功，存款:" + money + "元,当前余额是:" + loginAcc.getMoney());
    }

    private void showLoginAccount() {
        System.out.println("==您的账户信息如下：==");
        System.out.println("您的卡号是:" + loginAcc.getCardId());
        System.out.println("账户名称是:" + loginAcc.getUserName());
        System.out.println("您的余额是:" + loginAcc.getMoney());
        System.out.println("您的取款限额是:" + loginAcc.getLimit());
        System.out.println("性别是:" + loginAcc.getSex());
    }

    /** 完成用户开户操作 */
    private void createAccount() {
        // 1.创建一个账户对象
        Account acc = new Account();
        // 2.需要用户输入自己的开户信息
        System.out.println("请输入你的账户名称：");
        String name = sc.next();
        acc.setUserName(name);

        while (true) {
            System.out.println("请输入您的性别：");
            char sex = sc.next().charAt(0);
            if (sex == '男' || sex == '女') {
                acc.setSex(sex);
                break;
            } else {
                System.out.println("输入的性别有误，只能是男或者女，请重新输入");
            }
        }

        while (true) {
            System.out.println("请输入您的账户密码：");
            String passWord = sc.next();
            System.out.println("请输入您的确认密码：");
            String okPassWord = sc.next();
            if (okPassWord.equals(passWord)) {
                acc.setPassword(okPassWord);
                break;
            } else {
                System.out.println("两次密码不一致，请您确认");
            }
        }

        System.out.println("请输入你的取现额度");
        double limit = sc.nextDouble();
        acc.setLimit(limit);

        // 系统随机数生成卡号(8位数字表示，不能与别的卡号重复)
        String newCardId = createCardId();
        acc.setCardId(newCardId);

        // 3. 把这个账户对象,存入到账户集合中去
        accounts.add(acc);
        System.out.println("恭喜您," + acc.getUserName() + "开户成功，您的账户是" + acc.getCardId());
    }

    /** 返回一个8位的卡号，不能重复 */
    public String createCardId() {
        while (true) {
            String cardId = "";
            Random random = new Random();
            // 循环八次
            for (int i = 0; i < 8; i++) {
                int data = random.nextInt(10);
                cardId += data;
            }
            // 不能重复
            Account acc = getAccountBycardId(cardId);
            if (acc == null) {
                // 没有重复
                return cardId;
            }
        }
    }

    /** 根据卡号查询对象 */
    private Account getAccountBycardId(String cardId) {
        for (int i = 0; i < accounts.size(); i++) {
            Account acc = accounts.get(i);
            if (acc.getCardId().equals(cardId)) {
                return acc;
            }

        }
        return null;
    }

}
