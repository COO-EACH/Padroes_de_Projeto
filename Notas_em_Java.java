//fabrica abstrata é:
Interface DaoAbstractFactory

Usuario creatUsuarioDao();
-EmprestimoDao
-LivroDao
-....

DaoJdbcFactory
-importa creatUsuarioDao
-instancia user DaoJdbcFactory
-return um UsuarioDaoJDBC
