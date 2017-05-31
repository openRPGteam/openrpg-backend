package info.openrpg.db.controllers;

import info.openrpg.db.controllers.exceptions.NonexistentEntityException;
import info.openrpg.db.model.ChunkDB;
import info.openrpg.db.model.WorldMap;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.List;

public class ChunkDBJpaController implements Serializable {

    private EntityManagerFactory emf = null;

    public ChunkDBJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ChunkDB chunkDB) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            WorldMap worldMap = chunkDB.getWorldMap();
            if (worldMap != null) {
                worldMap = em.getReference(worldMap.getClass(), worldMap.getWorld_id());
                chunkDB.setWorldMap(worldMap);
            }
            em.persist(chunkDB);
            if (worldMap != null) {
                worldMap.getChunksList().add(chunkDB);
                worldMap = em.merge(worldMap);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ChunkDB chunkDB) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ChunkDB persistentChunkDB = em.find(ChunkDB.class, chunkDB.getChunk_id());
            WorldMap worldMapOld = persistentChunkDB.getWorldMap();
            WorldMap worldMapNew = chunkDB.getWorldMap();
            if (worldMapNew != null) {
                worldMapNew = em.getReference(worldMapNew.getClass(), worldMapNew.getWorld_id());
                chunkDB.setWorldMap(worldMapNew);
            }
            chunkDB = em.merge(chunkDB);
            if (worldMapOld != null && !worldMapOld.equals(worldMapNew)) {
                worldMapOld.getChunksList().remove(chunkDB);
                worldMapOld = em.merge(worldMapOld);
            }
            if (worldMapNew != null && !worldMapNew.equals(worldMapOld)) {
                worldMapNew.getChunksList().add(chunkDB);
                worldMapNew = em.merge(worldMapNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = chunkDB.getChunk_id();
                if (findChunkDB(id) == null) {
                    throw new NonexistentEntityException("The chunkDB with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ChunkDB chunkDB;
            try {
                chunkDB = em.getReference(ChunkDB.class, id);
                chunkDB.getChunk_id();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The chunkDB with id " + id + " no longer exists.", enfe);
            }
            WorldMap worldMap = chunkDB.getWorldMap();
            if (worldMap != null) {
                worldMap.getChunksList().remove(chunkDB);
                worldMap = em.merge(worldMap);
            }
            em.remove(chunkDB);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ChunkDB> findChunkDBEntities() {
        return findChunkDBEntities(true, -1, -1);
    }

    public List<ChunkDB> findChunkDBEntities(int maxResults, int firstResult) {
        return findChunkDBEntities(false, maxResults, firstResult);
    }

    private List<ChunkDB> findChunkDBEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ChunkDB.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public ChunkDB findChunkDB(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ChunkDB.class, id);
        } finally {
            em.close();
        }
    }

    public int getChunkDBCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ChunkDB> rt = cq.from(ChunkDB.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
