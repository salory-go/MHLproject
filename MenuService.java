package service;

import dao.BasicDAO;
import dao.MenuDAO;
import domain.Bill;
import domain.Menu;

import java.util.List;

public class MenuService {
    private MenuDAO menuDAO = new MenuDAO();

    //返回所有菜品
    public List<Menu> list(){
        return  (List<Menu>) menuDAO.queryMulti("select * from menu",Menu.class);
    }

    //根据ID返回menu对象
    public Menu getMenuById(int id){
        return (Menu) menuDAO.querySingle("select * from menu where id =?",Menu.class,id);
    }
}
