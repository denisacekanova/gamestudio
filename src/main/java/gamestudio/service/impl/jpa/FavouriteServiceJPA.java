package gamestudio.service.impl.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import gamestudio.entity.Favourite;
import gamestudio.service.FavouriteService;

@Transactional
public class FavouriteServiceJPA implements FavouriteService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void addFavourite(Favourite favourite) {
		try {
			Favourite f = (Favourite)entityManager.createQuery("SELECT f FROM Favourite f WHERE f.username =:username AND f.game =:game")
					.setParameter("username", favourite.getUsername()).setParameter("game", favourite.getGame())
					.getSingleResult();

			entityManager.remove(f);
		} catch (NoResultException e) {
			entityManager.persist(favourite);
		}

	}

	@Override
	public boolean isFavourite(Favourite favourite) {

		try {
			entityManager.createQuery("SELECT f FROM Favourite f WHERE f.username =:username AND f.game =:game")
					.setParameter("username", favourite.getUsername()).setParameter("game", favourite.getGame())
					.getSingleResult();
			return true;
		} catch (NoResultException e) {
			
		}
		return false;
	}

	@Override
	public List<Favourite> getFavourite(String username) {
		return entityManager.createQuery("SELECT f FROM Favourite f WHERE f.username =:username")
				.setParameter("username", username).getResultList();
	}

}