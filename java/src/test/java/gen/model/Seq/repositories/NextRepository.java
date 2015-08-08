package gen.model.Seq.repositories;



public class NextRepository   implements org.revenj.patterns.Repository<gen.model.Seq.Next>, org.revenj.patterns.PersistableRepository<gen.model.Seq.Next> {
	
	
	
	public NextRepository(
			 final java.sql.Connection connection,
			 final org.revenj.postgres.QueryProvider queryProvider,
			 final org.revenj.postgres.ObjectConverter<gen.model.Seq.Next> converter,
			 final org.revenj.patterns.ServiceLocator locator) {
			
		this.connection = connection;
		this.queryProvider = queryProvider;
		this.converter = converter;
		this.locator = locator;
	}

	private final java.sql.Connection connection;
	private final org.revenj.postgres.QueryProvider queryProvider;
	private final org.revenj.postgres.ObjectConverter<gen.model.Seq.Next> converter;
	private final org.revenj.patterns.ServiceLocator locator;
	
	public NextRepository(org.revenj.patterns.ServiceLocator locator) {
		this(locator.resolve(java.sql.Connection.class), locator.resolve(org.revenj.postgres.QueryProvider.class), new org.revenj.patterns.Generic<org.revenj.postgres.ObjectConverter<gen.model.Seq.Next>>(){}.resolve(locator), locator);
	}
	
	@Override
	public org.revenj.patterns.Query<gen.model.Seq.Next> query(org.revenj.patterns.Specification<gen.model.Seq.Next> filter) {
		org.revenj.patterns.Query<gen.model.Seq.Next> query = queryProvider.query(connection, locator, gen.model.Seq.Next.class);
		if (filter == null) return query;
		
		
		if (filter instanceof gen.model.Seq.Next.BetweenIds) {
			gen.model.Seq.Next.BetweenIds _spec_ = (gen.model.Seq.Next.BetweenIds)filter;
			Integer _spec_min_ = _spec_.getMin();
			int _spec_max_ = _spec_.getMax();
			return query.filter(it -> ( _spec_min_ == null ||  ( (it.getID() >= _spec_min_) &&  (it.getID() <= _spec_max_))));
		}		
		return query.filter(filter);
	}

	private java.util.ArrayList<gen.model.Seq.Next> readFromDb(java.sql.PreparedStatement statement, java.util.ArrayList<gen.model.Seq.Next> result) throws java.sql.SQLException, java.io.IOException {
		org.revenj.postgres.PostgresReader reader = new org.revenj.postgres.PostgresReader(locator);
		try (java.sql.ResultSet rs = statement.executeQuery()) {
			while (rs.next()) {
				org.postgresql.util.PGobject pgo = (org.postgresql.util.PGobject) rs.getObject(1);
				reader.process(pgo.getValue());
				result.add(converter.from(reader));
			}
		}
		return result;
	}

	@Override
	public java.util.List<gen.model.Seq.Next> search(java.util.Optional<org.revenj.patterns.Specification<gen.model.Seq.Next>> filter, java.util.Optional<Integer> limit, java.util.Optional<Integer> offset) {
		String sql = null;
		if (filter == null || filter.orElse(null) == null) {
			sql = "SELECT r FROM \"Seq\".\"Next_entity\" r";
			if (limit != null && limit.orElse(null) != null) {
				sql += " LIMIT " + Integer.toString(limit.get());
			}
			if (offset != null && offset.orElse(null) != null) {
				sql += " OFFSET " + Integer.toString(offset.get());
			}
			try (java.sql.PreparedStatement statement = connection.prepareStatement(sql)) {
				return readFromDb(statement, new java.util.ArrayList<>());
			} catch (java.sql.SQLException | java.io.IOException e) {
				throw new RuntimeException(e);
			}
		}
		org.revenj.patterns.Specification<gen.model.Seq.Next> specification = filter.get();
		java.util.function.Consumer<java.sql.PreparedStatement> applyFilters = ps -> {};
		try (org.revenj.postgres.PostgresWriter pgWriter = org.revenj.postgres.PostgresWriter.create()) {
			
		
		if (specification instanceof gen.model.Seq.Next.BetweenIds) {
			gen.model.Seq.Next.BetweenIds spec = (gen.model.Seq.Next.BetweenIds)specification;
			sql = "SELECT it FROM \"Seq\".\"Next.BetweenIds\"(?, ?) it";
			
			applyFilters = applyFilters.andThen(ps -> {
				try {
					if (spec.getMin() == null) ps.setNull(1, java.sql.Types.INTEGER); 
					else ps.setInt(1, spec.getMin());
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			});
			applyFilters = applyFilters.andThen(ps -> {
				try {
					ps.setInt(2, spec.getMax());
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			});
		}
			if (sql != null) {
				if (limit != null && limit.orElse(null) != null) {
					sql += " LIMIT " + Integer.toString(limit.get());
				}
				if (offset != null && offset.orElse(null) != null) {
					sql += " OFFSET " + Integer.toString(offset.get());
				}
				try (java.sql.PreparedStatement statement = connection.prepareStatement(sql)) {
					applyFilters.accept(statement);
					return readFromDb(statement, new java.util.ArrayList<>());
				} catch (java.sql.SQLException | java.io.IOException e) {
					throw new RuntimeException(e);
				}
			}
			org.revenj.patterns.Query<gen.model.Seq.Next> query = query(specification);
			if (offset != null && offset.orElse(null) != null) {
				query = query.skip(offset.get());
			}
			if (limit != null && limit.orElse(null) != null) {
				query = query.limit(limit.get());
			}
			try {
				return query.list();
			} catch (java.io.IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	
	@Override
	public java.util.List<gen.model.Seq.Next> find(String[] uris) {
		try (java.sql.Statement statement = connection.createStatement()) {
			java.util.ArrayList<gen.model.Seq.Next> result = new java.util.ArrayList<>(uris.length);
			org.revenj.postgres.PostgresReader reader = new org.revenj.postgres.PostgresReader(locator);
			StringBuilder sb = new StringBuilder("SELECT r FROM \"Seq\".\"Next_entity\" r WHERE r.\"ID\" IN (");
			org.revenj.postgres.PostgresWriter.writeSimpleUriList(sb, uris);
			sb.append(")");
			try (java.sql.ResultSet rs = statement.executeQuery(sb.toString())) {
				while (rs.next()) {
					org.postgresql.util.PGobject pgo = (org.postgresql.util.PGobject) rs.getObject(1);
					reader.process(pgo.getValue());
					result.add(converter.from(reader));
				}
			}
			return result;
		} catch (java.sql.SQLException | java.io.IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public java.util.List<String> persist(
			java.util.Collection<gen.model.Seq.Next> insert,
			java.util.Collection<java.util.Map.Entry<gen.model.Seq.Next, gen.model.Seq.Next>> update,
			java.util.Collection<gen.model.Seq.Next> delete) throws java.io.IOException {
		try (java.sql.PreparedStatement statement = connection.prepareStatement("/*NO LOAD BALANCE*/SELECT * FROM \"Seq\".\"persist_Next\"(?, ?, ?, ?)");
			org.revenj.postgres.PostgresWriter sw = org.revenj.postgres.PostgresWriter.create()) {
			java.util.List<String> result;
			if (insert != null && !insert.isEmpty()) {
		
				if (assignSequenceID == null) throw new RuntimeException("Next repository has not been properly set up. Static __setupSequenceID method not called");
				assignSequenceID.accept(insert, connection);
				result = new java.util.ArrayList<>(insert.size());
				org.revenj.postgres.converters.PostgresTuple tuple = org.revenj.postgres.converters.ArrayTuple.create(insert, converter::to);
				org.postgresql.util.PGobject pgo = new org.postgresql.util.PGobject();
				pgo.setType("\"Seq\".\"Next_entity\"[]");
				tuple.buildTuple(sw, false);
				pgo.setValue(sw.toString());
				sw.reset();
				statement.setObject(1, pgo);
				for (gen.model.Seq.Next it : insert) {
					String uri = gen.model.Seq.converters.NextConverter.buildURI(sw.tmp, it.getID());
					result.add(uri);
				}
			} else {
				statement.setArray(1, null);
				result = new java.util.ArrayList<>(0);
			}
			if (update != null && !update.isEmpty()) {
				java.util.List<gen.model.Seq.Next> oldUpdate = new java.util.ArrayList<>(update.size());
				java.util.List<gen.model.Seq.Next> newUpdate = new java.util.ArrayList<>(update.size());
				java.util.Map<String, Integer> missing = new java.util.HashMap<>();
				int cnt = 0;
				for (java.util.Map.Entry<gen.model.Seq.Next, gen.model.Seq.Next> it : update) {
					oldUpdate.add(it.getKey());
					if (it.getKey() == null) {
						missing.put(it.getValue().getURI(), cnt);
					}
					newUpdate.add(it.getValue());
					cnt++;
				}
				if (!missing.isEmpty()) {
					java.util.List<gen.model.Seq.Next> found = find(missing.keySet().toArray(new String[missing.size()]));
					for (gen.model.Seq.Next it : found) {
						oldUpdate.set(missing.get(it.getURI()), it);
					}
				}
				org.revenj.postgres.converters.PostgresTuple tupleOld = org.revenj.postgres.converters.ArrayTuple.create(oldUpdate, converter::to);
				org.revenj.postgres.converters.PostgresTuple tupleNew = org.revenj.postgres.converters.ArrayTuple.create(newUpdate, converter::to);
				org.postgresql.util.PGobject pgOld = new org.postgresql.util.PGobject();
				org.postgresql.util.PGobject pgNew = new org.postgresql.util.PGobject();
				pgOld.setType("\"Seq\".\"Next_entity\"[]");
				pgNew.setType("\"Seq\".\"Next_entity\"[]");
				tupleOld.buildTuple(sw, false);
				pgOld.setValue(sw.toString());
				sw.reset();
				tupleNew.buildTuple(sw, false);
				pgNew.setValue(sw.toString());
				sw.reset();
				statement.setObject(2, pgOld);
				statement.setObject(3, pgNew);
			} else {
				statement.setArray(2, null);
				statement.setArray(3, null);
			}
			if (delete != null && !delete.isEmpty()) {
				org.revenj.postgres.converters.PostgresTuple tuple = org.revenj.postgres.converters.ArrayTuple.create(delete, converter::to);
				org.postgresql.util.PGobject pgo = new org.postgresql.util.PGobject();
				pgo.setType("\"Seq\".\"Next_entity\"[]");
				tuple.buildTuple(sw, false);
				pgo.setValue(sw.toString());
				statement.setObject(4, pgo);
			} else {
				statement.setArray(4, null);
			}
			try (java.sql.ResultSet rs = statement.executeQuery()) {
				rs.next();
				String message = rs.getString(1);
				if (message != null) throw new java.io.IOException(message);
			}
			return result;
		} catch (java.sql.SQLException e) {
			throw new java.io.IOException(e);
		}
	}

	
	public static void __setupSequenceID(java.util.function.BiConsumer<java.util.Collection<gen.model.Seq.Next>, java.sql.Connection> sequence) {
		assignSequenceID = sequence;
	}

	private static java.util.function.BiConsumer<java.util.Collection<gen.model.Seq.Next>, java.sql.Connection> assignSequenceID;
}
