package com.example.di_1_hexentanz.wifi.network.mordechaim_server;


import com.example.di_1_hexentanz.wifi.network.messages.std.StartMessage;

public class ClientAdapter implements ClientListener {

	@Override
	public void messageReceived(Client client, Object msg) {
		// TODO Auto-generated method stub
		if(msg instanceof StartMessage){

		}
	}

	@Override
	public void commandReceived(Client client, Command cmd) {
		// TODO Auto-generated method stub

	}

	@Override
	public void disconnected(Client client) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void messageSent(Client client, Object msg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void commandSent(Client client, Command cmd) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void connected(Client client) {
		// TODO Auto-generated method stub
		
	}


}
