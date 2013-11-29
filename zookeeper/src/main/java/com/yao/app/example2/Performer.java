package com.yao.app.example2;


public class Performer implements Runnable {

	private Barrier barrier;

	public Performer(String host, Integer count) throws Exception {
		barrier = new Barrier(host, count);
	}

	@Override
	public void run() {
		try {
			long id = Thread.currentThread().getId();
			System.out.println("Performer " + id + " is ready");
			barrier.ensureAllParticipantsAreReady();
			System.out.println("Performer " + id + " is performming");
			barrier.leave();
			System.out.println("Performer " + id + " fininshed");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
