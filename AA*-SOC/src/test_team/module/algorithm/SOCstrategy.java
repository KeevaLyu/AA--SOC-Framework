package test_team.module.algorithm;

import adf.agent.develop.DevelopData;
import adf.agent.info.AgentInfo;
import adf.agent.info.ScenarioInfo;
import adf.agent.info.WorldInfo;
import adf.agent.module.ModuleManager;
import adf.agent.precompute.PrecomputeData;
import adf.component.module.algorithm.PathPlanning;
import rescuecore2.misc.collections.LazyMap;
//import rescuecore2.standard.entities.Area;
import rescuecore2.standard.entities.*;
import rescuecore2.worldmodel.Entity;
import rescuecore2.worldmodel.EntityID;

import java.util.*;

public class SOCstrategy extends PathPlanning {

   private Map<EntityID, Set<EntityID>> graph;

   private EntityID from;
   private Collection<EntityID> targets;
   private List<EntityID> result;

   public SOCstrategy(AgentInfo ai, WorldInfo wi, ScenarioInfo si, ModuleManager moduleManager, DevelopData developData) {
       super(ai, wi, si, moduleManager, developData);
       this.init();
   }

   private void init() {
       Map<EntityID, Set<EntityID>> neighbours = new LazyMap<EntityID, Set<EntityID>>() {
           @Override
           public Set<EntityID> createValue() {
               return new HashSet<>();
           }
       };
       for (Entity next : this.worldInfo) {
           if (next instanceof Area) {
               Collection<EntityID> areaNeighbours = ((Area) next).getNeighbours();
               neighbours.get(next.getID()).addAll(areaNeighbours);
           }
       }
       this.graph = neighbours;
   }

   @Override
   public List<EntityID> getResult() {
       return this.result;
   }

   @Override
   public PathPlanning setFrom(EntityID id) {
       this.from = id;
       return this;
   }

   @Override
   public PathPlanning setDestination(Collection<EntityID> targets) {
     this.targets = targets;
     return this;
   }

   @Override
   public PathPlanning precompute(PrecomputeData precomputeData) {
       super.precompute(precomputeData);
       return this;
   }

   @Override
   public PathPlanning resume(PrecomputeData precomputeData) {
       super.resume(precomputeData);
       return this;
   }

   @Override
   public PathPlanning preparate() {
       super.preparate();
       return this;
   }

   @Override
   public PathPlanning calc() {
       //  1
       List<EntityID> open = new LinkedList<>();
       List<EntityID> close = new LinkedList<>();
       Map<EntityID, Node> nodeMap = new HashMap<>();

       //  3
       open.add(this.from);
       nodeMap.put(this.from, new Node(null, this.from));
       close.clear();

       while (true) {
           //  4
           if (open.size() < 0) {
               this.result = null;
               return this;
           }

           //  5
           Node n = null;
           for (EntityID id : open) {
               Node node = nodeMap.get(id);

               if (n == null) {
                   n = node;
               } else if (node.estimate() < n.estimate()) {
                   n = node;
               }
           }

           //  6
           if (targets.contains(n.getID())) {
               //  9
               List<EntityID> path = new LinkedList<>();
               while (n != null) {
                   path.add(0, n.getID());
                   n = nodeMap.get(n.getParent());
               }

               this.result = path;
               return this;
           }
           open.remove(n.getID());
           close.add(n.getID());

           //  7
//            Collection<EntityID> neighbours = this.graph.get(n.getID());
//                for (EntityID neighbour : neighbours) {
//                    Node m = new Node(n, neighbour);
//
//                    if (!open.contains(neighbour) && !close.contains(neighbour)) {
//                        open.add(m.getID());
//                        nodeMap.put(neighbour, m);
//                    }
//                    else if (open.contains(neighbour) && m.estimate() < nodeMap.get(neighbour).estimate()) {
//                        nodeMap.put(neighbour, m);
//                    }
//                    else if (!close.contains(neighbour) && m.estimate() < nodeMap.get(neighbour).estimate()) {
//                        nodeMap.put(neighbour, m);
//                    }
//                }
           Collection<EntityID> neighbours = this.graph.get(n.getID());
           for (EntityID neighbour : neighbours) {
               Node m = new Node(n, neighbour);

               // Check if the direct path from next to neighbour is feasible
               if (!isLineOfSightBlocked(n.getID(), neighbour)) {
                   if (isGoal(neighbour, targets)) {
                       nodeMap.put(neighbour, m);
                       if (!open.contains(neighbour)){
                           open.add(m.getID());
                       }
                   } else {
                       if (!open.contains(neighbour) && !close.contains(neighbour)) {
                           open.add(m.getID());
                           nodeMap.put(neighbour, m);
                       }
                       else if (open.contains(neighbour) && m.estimate() < nodeMap.get(neighbour).estimate()) {
                           nodeMap.put(neighbour, m);
                       }
                       else if (!close.contains(neighbour) && m.estimate() < nodeMap.get(neighbour).estimate()) {
                           nodeMap.put(neighbour, m);
                       }
                   }
               }
           }
       }
   }

   // Check if there is a line of sight between two nodes
   private boolean isLineOfSightBlocked(EntityID from, EntityID to) {
       // 获取起始位置和目标位置的实体对象
       Entity fromEntity = this.worldInfo.getEntity(from);
       Entity toEntity = this.worldInfo.getEntity(to);

       // 确保起始位置和目标位置都存在
       if (fromEntity == null || toEntity == null) {
           return true; // 如果其中一个位置不存在实体，则直接返回阻塞
       }

       // 检查起始位置和目标位置之间是否有障碍物
       StandardEntity fromE = this.worldInfo.getEntity(from);
       Area fromArea = (Area) fromE;
       List<Edge> edges1 = fromArea.getEdges();
       StandardEntity toE = this.worldInfo.getEntity(to);
       Area toArea = (Area) toE;
       List<Edge> edges2 = toArea.getEdges();
       for (Edge edge1 : edges1){
            for (Edge edge2 : edges2){
                if ((edge1 == edge2) && (edge1.isPassable())){
                    return true;
                }
            }
       }
       return false;
   }

   private boolean isGoal(EntityID e, Collection<EntityID> test) {
       return test.contains(e);
   }

   private class Node {
	    EntityID id;
	    EntityID parent;

	    double cost;
	    double heuristic;

	    public Node(Node from, EntityID id) {
	        this.id = id;

	        if (from == null) {
	            this.cost = 0;
	        } else {
	            this.parent = from.getID();
	            this.cost = from.getCost() + worldInfo.getDistance(from.getID(), id);
	        }

	        this.heuristic = worldInfo.getDistance(id, targets.toArray(new EntityID[targets.size()])[0]);
	    }

	    public EntityID getID() {
	        return id;
	    }

	    public double getCost() {
	        return cost;
	    }

	    public double estimate() {
	        return cost + 1.5 * heuristic;
	    }

	    public EntityID getParent() {
	        return this.parent;
	    }
	}
}
