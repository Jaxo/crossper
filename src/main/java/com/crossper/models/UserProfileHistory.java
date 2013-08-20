package com.crossper.models;

import java.util.*;

public class UserProfileHistory extends AuditBase {

	private int user_id;
	private List <ClaimedOfferHistory> claimedOfferHistory;
	private List<PriceLevelHistory> priceLevelHistory;

}
