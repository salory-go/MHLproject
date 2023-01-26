package view;

import dao.MultiTableDAO;
import domain.*;
import javafx.scene.effect.Bloom;
import service.BillService;
import service.DiningTabelService;
import service.EmployeeService;
import service.MenuService;
import sun.font.DelegatingShape;
import utils.Utility;

import java.util.List;

/**
 *  main window
 */
public class MHLView {
    private boolean loop = true;
    private String key;
    private EmployeeService employeeService = new EmployeeService();
    private DiningTabelService diningTabelService = new DiningTabelService();
    private MenuService menuService = new MenuService();
    private BillService billService = new BillService();

    public static void main(String[] args) {
        new MHLView().mainMenu();
    }


    //完成结账
    public void payBill(){
        System.out.println("==============结账服务==============");
        System.out.print("请选择要结账的餐桌编号（-1退出）：");
        int diningTableId = Utility.readInt();
        if(diningTableId==-1){
            System.out.println("==============取消结账==============");
            return;
        }
        DiningTable diningTable = diningTabelService.getDiningTableById(diningTableId);
        if(diningTable==null){
            System.out.println("==============结账的餐桌不存在==============");
            return;
        }
        if (!billService.hasPayBillDiningTableId(diningTableId)) {
            System.out.println("==============该餐位已结账==============");
            return;
        }
        System.out.print("结账方式（现金/支付宝/微信）回车表示退出：");
        String payMode = Utility.readString(20, "");
        if(payMode.equals("")){
            System.out.println("==============取消结账==============");
            return;
        }
        char key = Utility.readConfirmSelection();
        if (key == 'Y') {
            if (billService.payBill(diningTableId,payMode)) {
                System.out.println("==============结账成功==============");
            }else {
                System.out.println("==============结账失败==============");
            }
        }
        else {
            System.out.println("==============取消结账==============");
        }
    }

    //显示账单
    public void listBill(){
        System.out.println("\n编号\t\t菜品号\t\t菜品量\t\t金额\t\t桌号\t\t日期\t\t\t\t\t\t\t状态\t\t菜品名\t\t单价");
        List<MultiTableBean> bill = billService.list2();
        for(MultiTableBean b:bill){
            System.out.println(b);
        }
    }

    //完成点餐
    public void orderMenu(){
        System.out.println("==============点餐服务==============");
        System.out.print("请输入点餐的桌号（-1退出）：");
        int orderDiningTableId = Utility.readInt();
        if(orderDiningTableId==-1){
            System.out.println("==============取消订餐==============");
            return;
        }
        System.out.print("请输入点餐的菜品号（-1退出）：");
        int orderMenuId = Utility.readInt();
        if(orderMenuId==-1){
            System.out.println("==============取消订餐==============");
            return;
        }
        System.out.print("请输入点餐的菜品量（-1退出）：");
        int orderMenuSums = Utility.readInt();
        if(orderMenuSums==-1){
            System.out.println("==============取消订餐==============");
            return;
        }
        //验证餐桌号存在
        DiningTable diningTable = diningTabelService.getDiningTableById(orderDiningTableId);
        if(diningTable==null){
            System.out.println("==============餐号不存在==============");
            return;
        }
        //验证菜品号存在
        Menu menu = menuService.getMenuById(orderMenuId);
        if(menu==null){
            System.out.println("==============菜品号不存在==============");
            return;
        }
        if (billService.orderMenu(orderMenuId,orderMenuSums,orderDiningTableId)) {
            System.out.println("==============点餐成功==============");
        }else {
            System.out.println("==============点餐失败==============");

        }
    }

    //显示所有菜品
    public void listMenu()  {
        List<Menu> list = menuService.list();
        System.out.println("\n菜品编号\t\t餐桌名\t\t类别\t\t价格");
        for(Menu menu:list){
            System.out.println(menu);
        }
        System.out.println("==============显示完毕==============");
    }

    //预定餐桌
    public void orderDiningTable(){
        System.out.println("==============预定餐桌==============");
        System.out.print("请选择预定的餐桌编号（-1退出）：");
        int orderId = Utility.readInt();
        if(orderId==-1){
            System.out.println("==============取消预定餐桌==============");
            return;
        }
        char key = Utility.readConfirmSelection();
        if(key=='Y'){
            DiningTable diningTable = diningTabelService.getDiningTableById(orderId);
            if(diningTable==null){
                System.out.println("==============餐桌不存在==============");
                return;
            }
            if(!"空".equals(diningTable.getState())){
                System.out.println("==============餐桌已预订或就餐中==============");
                return;
            }
            System.out.println("预订人姓名：");
            String orderName = Utility.readString(50);
            System.out.println("预订人电话：");
            String orderTel = Utility.readString(50);
            if (diningTabelService.orderDiningTable(orderId,orderName,orderTel)) {
                System.out.println("==============预定成功==============");
            }
            else{
                System.out.println("==============预定失败==============");

            }
        }else {
            System.out.println("==============取消预定餐桌==============");
        }
    }

    //显示餐桌
    public void listDiningTable(){
        List<DiningTable> list = diningTabelService.list();
        System.out.println("\n餐桌编号\t\t餐桌状态");
        for(DiningTable diningTable:list){
            System.out.println(diningTable);
        }
        System.out.println("==============显示完毕==============");
    }

    public void mainMenu() {
        while(loop){
            System.out.println("==============满汉楼==============");
            System.out.println("\t\t1.登录满汉楼");
            System.out.println("\t\t2.退出满汉楼");
            System.out.print("请输入你的选择：");
            key = Utility.readString(1);
            switch (key){
                case "1":
                    System.out.print("请输入员工号：");
                    String empid = Utility.readString(50);
                    System.out.print("请输入密码：");
                    String pwd = Utility.readString(50);
                    Employee emplyee = employeeService.getEmplyeeByIdAndPwd(empid, pwd);
                    if(emplyee!=null){
                        System.out.println("==============登录成功["+emplyee.getName()+"]==============");
                        while(loop){
                            System.out.println("==============满汉楼（二级菜单）==============");
                            System.out.println("\t\t1.显示餐桌状态");
                            System.out.println("\t\t2.预定餐桌");
                            System.out.println("\t\t3.显示所有菜品");
                            System.out.println("\t\t4.点餐服务");
                            System.out.println("\t\t5.账单");
                            System.out.println("\t\t6.结账");
                            System.out.println("\t\t9.退出");
                            key = Utility.readString(1);
                            switch (key){
                                case "1":
                                    listDiningTable();
                                    break;
                                case "2":
                                    orderDiningTable();
                                    break;
                                case "3":
                                    listMenu();
                                    break;
                                case "4":
                                    orderMenu();
                                    break;
                                case "5":
                                    listBill();
                                    break;
                                case "6":
                                    payBill();
                                    break;
                                case "9":
                                    loop=false;
                                    break;
                                default:
                                    System.out.println("你输入有误，请重新输入");
                            }
                        }
                    }
                    else{
                        System.out.println("==============登录失败==============");
                    }
                    break;
                case "2":
                    loop = false;
                    break;
                default:
                    System.out.println("你输入有误，请重新输入");
            }
        }

        System.out.println("正在退出满汉楼.....");
    }
}
