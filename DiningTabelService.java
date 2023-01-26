package service;

import dao.DiningTableDAO;
import domain.DiningTable;

import java.util.List;

public class DiningTabelService {
    private DiningTableDAO diningTableDAO = new DiningTableDAO();

    public List<DiningTable> list(){
        return diningTableDAO.queryMulti("select id,state from diningtable", DiningTable.class);

    }
    //查询餐桌状态
    public DiningTable getDiningTableById(int id){

        return (DiningTable) diningTableDAO.querySingle("select * from diningtable where id =?",DiningTable.class,id);
    }
    //对可预定餐桌更新状态
    public boolean orderDiningTable(int id,String orderName,String orderTel){
        int update = diningTableDAO.update("update diningtable set state='已经预定',orderName = ?,orderTel=? where id = ? ",orderName,orderTel,id);
        return update>0;
    }

    public boolean updateDiningTableState(int id,String state){
        int update = diningTableDAO.update("update diningtable set state =? where id = ?", state, id);
        return update>0;
    }

    //提供方法，指定餐桌设置为空闲状态
    public boolean updateDiningTableToFree(int id,String state){
        int update = diningTableDAO.update("update diningtable set state =? , orderName='' ,orderTel='' where id = ?", state, id);
        return update>0;
    }
}
