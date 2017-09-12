package de.leuphana.ui.view.summoner;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.ExternalResource;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Label;
import com.vaadin.ui.Button.ClickShortcut;

import de.leuphana.app.security.SecurityUtils;
import de.leuphana.backend.service.AccountService;
import de.leuphana.backend.service.SummonerService;
import de.leuphana.ui.navigation.NavigationManager;
import de.leuphana.ui.util.DDragonUrlFormatter;
import de.leuphana.ui.view.champion.ChampionDetailView;
import net.rithms.riot.api.RiotApiException;
import net.rithms.riot.api.endpoints.static_data.dto.Champion;
import net.rithms.riot.api.endpoints.summoner.dto.Summoner;

@SpringView
public class SummonerView extends SummonerViewDesign implements View {

	private static final String PARAMETER_SEARCH = "search";
	private static final Logger logger = LoggerFactory.getLogger(SummonerView.class);
	private SummonerView view;
	private final NavigationManager navigationManager;
	private final DDragonUrlFormatter dDragonUrlFormatter = new DDragonUrlFormatter();
	

	@Autowired
	private SummonerService summonerService;
	@Autowired
	private AccountService userService;

	public SummonerView(NavigationManager navigationManager) {
		this.navigationManager = navigationManager;
	}

	@PostConstruct
	public void init() throws RiotApiException {
		searchPanel.addAction(new ClickShortcut(searchButton, KeyCode.ENTER, null));
		searchButton.addClickListener(e -> searchedSummoner(searchField.getValue()));
		searchButton.addClickListener(e -> search(searchField.getValue()));
	}

	private Object searchedSummoner(String value) {
			navigationManager.navigateTo(SummonerView.class, value);
			
			return null;
	}

	private void search(String searchTerm) {
		String parameters = PARAMETER_SEARCH + "=" + searchTerm;
		
		navigationManager.updateViewParameter(parameters);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		logger.info("Accessing enter Method in ChampionDetailView.class");
		Summoner summoner = null;
		String summonerName = event.getParameters();
		
		/*
		 * in case nothings typed into the searchfield -> SummonerName from Account gets displayed 
		 */
		
		if ("".equals(summonerName)) {
			summonerName = SecurityUtils.getCurrentUser(userService).getSummonerName();
			try {
				summoner = summonerService.findOneBySummonerName(summonerName);
			} catch (RiotApiException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			logger.info("Accessing SummonerView with SummonerName from SecurityUtils"+summoner.getName());
			try {
				setSummoner(summoner);
			} catch (RiotApiException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			/*
			 * 
			 * in case a param was typed into the searchfield
			 * 
			 */
		} else {
			try {
				summoner = summonerService.findOneBySummonerName(summonerName);
			} catch (Exception e) {
				view.showNotFound();
				return;
			}
			try {
				setSummoner(summoner);
			} catch (RiotApiException e) {
				e.printStackTrace();
			}
		}
	}

	

	private void setSummoner(Summoner summoner) throws RiotApiException {
		nameLabel.setValue(summoner.getName());
		levelLabel.setValue(String.valueOf(summoner.getSummonerLevel()));
		rankLabel.setValue(summonerService.getLeagueItemById(summoner.getId()).getRank());
		summonerImage.setSource(new ExternalResource(dDragonUrlFormatter.getUrlForSummonerIconByImageName(String.valueOf(summoner.getProfileIconId()))));
	}

	private void showNotFound() {
			removeAllComponents();
			addComponent(new Label("Summoner not found"));
		}
		
	}

