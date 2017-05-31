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
import java.util.ArrayList;
import java.util.List;

public class WorldMapJpaController implements Serializable {

    private EntityManagerFactory emf = null;

    public WorldMapJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(WorldMap worldMap) {
        if (worldMap.getChunksList() == null) {
            worldMap.setChunksList(new ArrayList<ChunkDB>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<ChunkDB> attachedChunksList = new ArrayList<ChunkDB>();
            for (ChunkDB chunksListChunkDBToAttach : worldMap.getChunksList()) {
                chunksListChunkDBToAttach = em.getReference(chunksListChunkDBToAttach.getClass(), chunksListChunkDBToAttach.getChunk_id());
                attachedChunksList.add(chunksListChunkDBToAttach);
            }
            worldMap.setChunksList(attachedChunksList);
            em.persist(worldMap);
            for (ChunkDB chunksListChunkDB : worldMap.getChunksList()) {
                WorldMap oldWorldMapOfChunksListChunkDB = chunksListChunkDB.getWorldMap();
                chunksListChunkDB.setWorldMap(worldMap);
                chunksListChunkDB = em.merge(chunksListChunkDB);
                if (oldWorldMapOfChunksListChunkDB != null) {
                    oldWorldMapOfChunksListChunkDB.getChunksList().remove(chunksListChunkDB);
                    oldWorldMapOfChunksListChunkDB = em.merge(oldWorldMapOfChunksListChunkDB);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(WorldMap worldMap) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            WorldMap persistentWorldMap = em.find(WorldMap.class, worldMap.getWorld_id());
            List<ChunkDB> chunksListOld = persistentWorldMap.getChunksList();
            List<ChunkDB> chunksListNew = worldMap.getChunksList();
            List<ChunkDB> attachedChunksListNew = new ArrayList<ChunkDB>();
            for (ChunkDB chunksListNewChunkDBToAttach : chunksListNew) {
                chunksListNewChunkDBToAttach = em.getReference(chunksListNewChunkDBToAttach.getClass(), chunksListNewChunkDBToAttach.getChunk_id());
                attachedChunksListNew.add(chunksListNewChunkDBToAttach);
            }
            chunksListNew = attachedChunksListNew;
            worldMap.setChunksList(chunksListNew);
            worldMap = em.merge(worldMap);
            for (ChunkDB chunksListOldChunkDB : chunksListOld) {
                if (!chunksListNew.contains(chunksListOldChunkDB)) {
                    chunksListOldChunkDB.setWorldMap(null);
                    chunksListOldChunkDB = em.merge(chunksListOldChunkDB);
                }
            }
            for (ChunkDB chunksListNewChunkDB : chunksListNew) {
                if (!chunksListOld.contains(chunksListNewChunkDB)) {
                    WorldMap oldWorldMapOfChunksListNewChunkDB = chunksListNewChunkDB.getWorldMap();
                    chunksListNewChunkDB.setWorldMap(worldMap);
                    chunksListNewChunkDB = em.merge(chunksListNewChunkDB);
                    if (oldWorldMapOfChunksListNewChunkDB != null && !oldWorldMapOfChunksListNewChunkDB.equals(worldMap)) {
                        oldWorldMapOfChunksListNewChunkDB.getChunksList().remove(chunksListNewChunkDB);
                        oldWorldMapOfChunksListNewChunkDB = em.merge(oldWorldMapOfChunksListNewChunkDB);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = worldMap.getWorld_id();
                if (findWorldMap(id) == null) {
                    throw new NonexistentEntityException("The worldMap with id " + id + " no longer exists.");
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
            WorldMap worldMap;
            try {
                worldMap = em.getReference(WorldMap.class, id);
                worldMap.getWorld_id();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The worldMap with id " + id + " no longer exists.", enfe);
            }
            List<ChunkDB> chunksList = worldMap.getChunksList();
            for (ChunkDB chunksListChunkDB : chunksList) {
                chunksListChunkDB.setWorldMap(null);
                chunksListChunkDB = em.merge(chunksListChunkDB);
            }
            em.remove(worldMap);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<WorldMap> findWorldMapEntities() {
        return findWorldMapEntities(true, -1, -1);
    }

    public List<WorldMap> findWorldMapEntities(int maxResults, int firstResult) {
        return findWorldMapEntities(false, maxResults, firstResult);
    }

    private List<WorldMap> findWorldMapEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(WorldMap.class));
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

    public WorldMap findWorldMap(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(WorldMap.class, id);
        } finally {
            em.close();
        }
    }

    public int getWorldMapCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<WorldMap> rt = cq.from(WorldMap.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
