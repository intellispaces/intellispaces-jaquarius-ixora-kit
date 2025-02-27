package tech.intellispaces.ixora.rdb.transaction;

import tech.intellispaces.ixora.rdb.datasource.DataSourceHandle;
import tech.intellispaces.ixora.rdb.datasource.MovableConnectionHandle;
import tech.intellispaces.ixora.rdb.datasource.MovableDataSourceHandle;
import tech.intellispaces.ixora.rdb.exception.TransactionException;
import tech.intellispaces.jaquarius.annotation.Mapper;
import tech.intellispaces.jaquarius.annotation.MapperOfMoving;
import tech.intellispaces.jaquarius.annotation.ObjectHandle;

@ObjectHandle(TransactionFactoryDomain.class)
abstract class TransactionFactoryHandleOverDataSource implements MovableTransactionFactoryHandle {
  private final MovableDataSourceHandle dataSource;

  TransactionFactoryHandleOverDataSource(MovableDataSourceHandle dataSource) {
    this.dataSource = dataSource;
  }

  @Mapper
  @Override
  public DataSourceHandle dataSource() {
    return dataSource;
  }

  @Override
  @MapperOfMoving
  public MovableTransactionHandle getTransaction() throws TransactionException {
    MovableConnectionHandle connection = dataSource.getConnection();
    return new TransactionHandleOverConnectionWrapper(connection);
  }
}
