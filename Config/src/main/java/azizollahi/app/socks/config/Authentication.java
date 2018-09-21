package azizollahi.app.socks.config;

import java.util.List;

public class Authentication {
	private List<Account> accounts;

	public List<Account> getAccounts() {
		return accounts;
	}

	public void setUsers(List<Account> users) {
		this.accounts = users;
	}
}
