package service;

import dao.BasicDAO;
import dao.BillDAO;
import dao.MultiTableDAO;
import domain.Bill;
import domain.DiningTable;
import domain.MultiTableBean;

import java.util.List;
import java.util.UUID;

public class BillService {
    private BillDAO billDAO = new BillDAO();
    private MenuService menuService = new MenuService();
    private DiningTabelService diningTabelService = new DiningTabelService();
    private MultiTableDAO multiTableDAO = new MultiTableDAO();

    //点餐,如果成真返回true
    public boolean orderMenu(int menuId,int nums,int diningTbleId){
        //生成一个不重复的账单号
        String billID = UUID.randomUUID().toString();

        int update = billDAO.update("insert into bill values(NULL,?,?,?,?,?,now(),'未结账')",
                billID, menuId, nums, menuService.getMenuById(menuId).getPrice() * nums, diningTbleId);
        if(update<=0){
            return false;
        }
        return diningTabelService.updateDiningTableState(diningTbleId,"就餐中");
    }

    //返回账单
    public List<Bill> list(){
        return (List<Bill>) billDAO.queryMulti("select * from bill",Bill.class);
    }

    //查看某个餐桌是否有未结账菜单
    public boolean hasPayBillDiningTableId(int diningTableId){
        Bill bill = (Bill)  billDAO.querySingle("select * from bill where diningTableId=? and state = '未结账' limit 0,1", Bill.class, diningTableId);
        return bill!=null;
    }

    //完成结账（如果餐桌存在，并且餐桌有未结账的账单）
    public boolean payBill(int diningTableId,String payMode){
        int update = billDAO.update("update bill set state =? where diningTableId=? and state='未结账'", payMode, diningTableId);
        if(update<=0){
            return false;
        }

        if(!diningTabelService.updateDiningTableToFree(diningTableId,"空")){
            return false;
        }

        return true;
    }

    public List<MultiTableBean> list2(){

        return (List<MultiTableBean>) multiTableDAO.queryMulti("SELECT bill.*,NAME,price\n" +
                "\tFROM bill,menu\n" +
                "\tWHERE bill.id=menu.id",MultiTableBean.class);
    }

}
