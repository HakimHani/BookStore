package main.java.com.springboot.bookshop.ctrl;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import main.java.com.springboot.bookshop.*;


public class CheckoutController {
	PurchaseOrderItem purchaseOrder;
	
    public CheckoutController() {
    	super();
    	purchaseOrder = new PurchaseOrderItem();
    }

	public void processParameters(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException{
		String username = (String) request.getSession().getAttribute("username");
		String name = request.getParameter("name");
		int i = name.indexOf(" ");
		String fname = name.substring(0, i); 
		String lname = name.substring(i + 1);
		CartBean currentCart;
		String usrnme;
		PrintWriter out = response.getWriter();
		
		if(username != null) 
		{
			currentCart = CartsListBean.getInstance().getCartByUsername(username);
			usrnme = username;
		}			
		else
		{
			currentCart = CartsListBean.getInstance().retrieveCart(request.getSession().getId());
			usrnme = "****";
		}
			int count = purchaseOrder.updatePO(usrnme, fname, lname, "ORDERED");
			if (count == -1) {
				out.println("<script type=\"text/javascript\">");
				   out.println("alert('cart empty!');");
				   out.println("</script>");
			}
			purchaseOrder.updatePOItem(usrnme, currentCart, count);
			currentCart.clearCart();
			response.getWriter().println(count);
			System.out.print(count);
	}
}