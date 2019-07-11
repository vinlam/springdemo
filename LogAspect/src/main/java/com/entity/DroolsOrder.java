package com.entity;

import java.util.Date;

public class DroolsOrder {
	      
	    private Date bookingDate;//下单日期
	      
	    private int amout;//订单原价金额  
	      
	    private DroolsUser user;//下单人  
	      
	    private int score;  
	      
	    public int getScore() {  
	        return score;  
	    }  
	  
	    public void setScore(int score) {  
	        this.score = score;  
	    }  
	  
	    public Date getBookingDate() {  
	        return bookingDate;  
	    }  
	  
	    public void setBookingDate(Date bookingDate) {  
	        this.bookingDate = bookingDate;  
	    }  
	  
	    public int getAmout() {  
	        return amout;  
	    }  
	  
	    public void setAmout(int amout) {  
	        this.amout = amout;  
	    }  
	  
	    public DroolsUser getUser() {  
	        return user;  
	    }  
	  
	    public void setUser(DroolsUser user) {  
	        this.user = user;  
	    }
}
