package de.leuphana.ui.view.admin.account;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.vaadin.artur.spring.dataprovider.FilterablePageableDataProvider;
import org.vaadin.spring.annotation.PrototypeScope;

import com.vaadin.data.provider.Query;
import com.vaadin.data.provider.QuerySortOrder;
import com.vaadin.shared.data.sort.SortDirection;
import com.vaadin.spring.annotation.SpringComponent;

import de.leuphana.backend.data.account.Account;
import de.leuphana.backend.service.account.AccountService;

@SpringComponent
@PrototypeScope
public class AccountAdminDataProvider extends FilterablePageableDataProvider<Account, Object> {

	private final AccountService accountService;

	@Autowired
	public AccountAdminDataProvider(AccountService accountService) {
		this.accountService = accountService;
	}

	@Override
	protected Page<Account> fetchFromBackEnd(Query<Account, Object> query, Pageable pageable) {
		return accountService.findAnyMatching(getOptionalFilter(), pageable);
	}

	@Override
	protected int sizeInBackEnd(Query<Account, Object> query) {
		return (int) accountService.countAnyMatching(getOptionalFilter());
	}

	@Override
	protected List<QuerySortOrder> getDefaultSortOrders() {
		List<QuerySortOrder> sortOrders = new ArrayList<>();
		sortOrders.add(new QuerySortOrder("email", SortDirection.ASCENDING));
		return sortOrders;
	}

}