package de.leuphana.ui.view.admin.account;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.annotations.DesignRoot;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.declarative.Design;
import de.leuphana.ui.view.admin.RoleComboBox;
import de.leuphana.ui.view.admin.WidgetListSelect;

/** 
 * !! DO NOT EDIT THIS FILE !!
 * 
 * This class is generated by Vaadin Designer and will be overwritten.
 * 
 * Please make a subclass with logic and additional interfaces as needed,
 * e.g class LoginView extends LoginDesign implements View { }
 */
@DesignRoot
@AutoGenerated
@SuppressWarnings("serial")
public class AccountAdminViewDesign extends VerticalLayout {
	protected TextField search;
	protected Button add;
	protected Grid<de.leuphana.backend.data.account.Account> accountGrid;
	protected VerticalLayout form;
	protected TextField email;
	protected TextField name;
	protected TextField password;
	protected TextField summonerName;
	protected RoleComboBox role;
	protected WidgetListSelect widgets;
	protected Button update;
	protected Button cancel;
	protected Button delete;

	public AccountAdminViewDesign() {
		Design.read(this);
	}
}
