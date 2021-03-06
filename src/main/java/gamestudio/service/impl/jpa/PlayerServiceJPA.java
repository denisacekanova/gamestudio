package gamestudio.service.impl.jpa;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import gamestudio.entity.Player;
import gamestudio.service.PlayerService;


@Transactional
public class PlayerServiceJPA implements PlayerService {
	@PersistenceContext
	private EntityManager entityManager;

	/* (non-Javadoc)
	 * @see gamestudio.service.impl.jpa.PlayerService#register(gamestudio.entity.Player)
	 */
	@Override
	public void register(Player player) {
		entityManager.persist(player);
	}

	/* (non-Javadoc)
	 * @see gamestudio.service.impl.jpa.PlayerService#login(java.lang.String, java.lang.String)
	 */
	@Override
	public Player login(String login, String password) {

		try {
			return (Player) entityManager
					.createQuery("SELECT p FROM Player p WHERE p.login = :login AND p.password = :password ")
					.setParameter("login", login).setParameter("password", password).getSingleResult();

		} catch (NoResultException e) {
			return null;
		}
	}
	
	@Override
	public boolean isLogin(String login) {

		try {
			 entityManager
					.createQuery("SELECT p FROM Player p WHERE p.login = :login").setParameter("login", login).getSingleResult();

		} catch (NoResultException e) {
			return false;
		}
		return true;
	}
}
