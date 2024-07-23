/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ma.fstm.ilisi.projectbusway.metier.service;

/**
 *
 * @author Aya
 */

import java.util.Scanner;

public class chatBot 
{
	private static final boolean TRACE_MODE=false;
	 
    public chatBot()
    {
    	/*CatalogueBus.getCatalogueBus();
        CatalogueStation.getCatalogueStation();
        Passager.CreerPassagers();
        Reservation.CreerReservations();
        Voyage.CreerVoyages();
        Bus.AssocierBusVoyage();*/
    }
	
	/*public static void main(String args[])
	{
		try 
		{
			String resourcepath = getpath();
			MagicBooleans.trace_mode = TRACE_MODE;
			Bot b = new Bot("sup",resourcepath);
			Chat chatsession = new Chat(b);
			String textline="";
			while(true)
			{
				System.out.println("YOU :");
		
				textline=IOUtils.readInputTextLine();
				if(textline==null || textline.length()<1)
					textline=MagicStrings.null_input;
				else if(textline.equals("q"))
						System.exit(0);
						else if(textline.equals("wq"))
							 {
								b.writeQuit();
								System.exit(0);
						     }
							else
							{
								String request=textline;
								String response=chatsession.multisentenceRespond(request);
								System.out.println("BOT:"+response);
							}
			}
		}
		catch (Exception e){}
	}*/
	
	public static void main(String[] args) 
	{
		chatBot c = new chatBot();
	    ReservationBot bot = new ReservationBot();
	    Scanner scanner = new Scanner(System.in);
	    System.out.println("Bienvenue dans notre système de réservation de tickets de bus!");
	    while (true) 
	    {
	        System.out.print("Vous: ");
	        String input = scanner.nextLine();
	        String respons=bot.chatSession.multisentenceRespond(input);
			System.out.println("Chatbot:"+respons);
	        String response = bot.processInput(input);
	        if(!respons.equals(response)) System.out.println("Chatbot: " + response);
	    }
	}

	
	
	
}
