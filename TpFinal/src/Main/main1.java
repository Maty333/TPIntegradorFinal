package Main;

	import java.sql.Connection;

	import java.sql.DriverManager;
	import java.sql.ResultSet;
	import java.sql.SQLException;
	import java.sql.Statement;
	import java.util.InputMismatchException;
	import java.util.Scanner;
	import java.util.logging.Level;
	import java.util.logging.Logger;

	
	public class main1{
	    static Connection conexion = null;
	    Statement sentencia = null; 

	   
	    public static void main(String[] args) {
	        Scanner sc = new Scanner(System.in);
	        main1 m = new 	main1();

	        m.conectar();    
	        boolean salir = false;
	        do {
	            switch (menuPrin()) {
	            	case 1:
	            		m.consultarPaciente();  
	            		break;
	            	case 2:  m.consultarMedico();
	            			break;
	            	case 3:
	            		m.agregarPac();   
	            		break;
	            	case 4:
	            		m.agregarMed();
	            		break;
	            	case 5: eliminarPac();	            	
	            		break;
	            	case 6: eliminarMed();
	            		break;
	            	case 7: ModRegPac();
	            	break;
	                case 0:
	                    System.out.println("Vuelva pronto");
	                    m.desconectar();             
	                    salir = true;
	                    break;
	                default:
	                    System.out.println("Opción incorrecta");
	                    break;
	            }
	        } while (!salir);

	    }
	//-------------------------------------------------------------------------------
	    
	    //MENU PRINCIPAL:
	    private static int menuPrin() {

	        Scanner sc = new Scanner(System.in);

	        System.out.println("--------------------------------");
	        System.out.println("Conexión de bbdd MySQL");
	        System.out.println("--------------------------------");
	        System.out.println("1.MOSTRAR EL CONTENIDO DE LA TABLA PACIENTES");
	        System.out.println("2.MOSTRAR EL CONTENIDO DE LA TABLA MEDICOS");
	        System.out.println("3.INSERTAR UN REGISTRO EN LA TABLA PACIENTES");
	        System.out.println("4.INSERTAR UN REGISTRO EN LA TABLA MEDICOS");
	        System.out.println("5.ELIMINAR UN REGISTRO EN LA TABLA PACIENTES");
	        System.out.println("6.ELIMINAR UN REGISTRO EN LA TABLA MEDICOS");
	        System.out.println("7.MODIFICAR UN REGISTRO DE UN PACIENTES");
	        System.out.println("0.SALIR");
	        System.out.println("\n Por favor, escoja una opción correcta.");
	        System.out.println("--------------------------------");

	        return sc.nextInt(); 

	    }

	//-----------------------------------------------------------------------------------------------
	    
	
	    public void conectar() {
	        try {
	            Class.forName("com.mysql.jdbc.Driver"); 
	             conexion = DriverManager.getConnection("jdbc:mysql://localhost/consultorio", "root", "matyLucy30!");	           
	            System.out.println("**************************************");
	            System.out.println(" * CONEXIÓN REALIZADA CORRECTAMENTE * ");
	            System.out.println("**************************************");
	        } catch (Exception e) {
	            System.out.println("*****************************************");
	            System.out.println(" * NO SE HA PODIDO REALIZAR LA CONEXIÓN * ");
	            System.out.println("******************************************");
	        }

	    }
	//-----------------------------------------------------------------------------------------------

	 
	    private void desconectar() {
	        try {
	            conexion.close(); 
	            System.out.println("\n************************************************************\n");
	            System.out.println("La conexion a la base de datos se ha terminado");
	            System.out.println("\n************************************************************");
	        } catch (SQLException ex) {
	            System.out.println(ex.getMessage());
	        }

	    }
	   
	//----------------------------------------------------------------------------------------------   

	/*MÉTODO PARA REALIZAR UNA CONSULTA A LA TABLA PACIENTES*/
	        private void consultarPaciente() {
	       
	        ResultSet r = buscar("select PacDni,PacNom,PacApe,PacDom from pacientes");  
	        try {
	            System.out.println("REGISTROS DE LA TABLA PACIENTES");
	            System.out.println("+-----------------------------------------------------+");
	            System.out.println("DNI | NOMBRE  | APELLIDO  | DOMICILIO");
	            System.out.println("+-----------------------------------------------------+");
	            
	            while (r.next()) {
	                
	                System.out.println(r.getInt("PacDni")+" " + " | " + r.getString("PacNom") + " | " + r.getString("PacApe") + " | " + r.getString("PacDom"));
	            }
	        } catch (SQLException ex) {
	         Logger.getLogger(main1.class.getName()).log(Level.SEVERE, null, ex);
	        }

	    }
	          
	    
	    ResultSet buscar(String sql) {
	        try {
	            sentencia = conexion.createStatement(); 
	            return sentencia.executeQuery(sql); 
	        } catch (SQLException ex) {
	            Logger.getLogger(main1.class.getName()).log(Level.SEVERE, null, ex);
	        }
	        return null;

	    }
 
	    ////////////////////////////////////////////////////////////////////////////////
	    
	    /*MÉTODO PARA REALIZAR UNA CONSULTA A LA TABLA MEDICOS*/
	    
	    private void consultarMedico() {
		       
	        ResultSet r = buscar1("select MedMat,MedNom,MedApe,MedEsp from medicos");  
	        try {
	            System.out.println("REGISTROS DE LA TABLA MEDICOS");
	            System.out.println("+-----------------------------------------------------+");
	            System.out.println("MATRICULA | NOMBRE  | APELLIDO  | ESPECIALIDAD");
	            System.out.println("+-----------------------------------------------------+");
	            
	            while (r.next()) {
	                
	                System.out.println(r.getInt("MedMat") + " | " + r.getString("MedNom") + " | " + r.getString("MedApe") + " | " + r.getString("MedEsp"));
	            }
	        } catch (SQLException ex) {
	         Logger.getLogger(main1.class.getName()).log(Level.SEVERE, null, ex);
	        }

	    }
	          
	    
	    ResultSet buscar1(String sql) {
	        try {
	            sentencia = conexion.createStatement(); 
	            return sentencia.executeQuery(sql); 
	        } catch (SQLException ex) {
	            Logger.getLogger(main1.class.getName()).log(Level.SEVERE, null, ex);
	        }
	        return null;

	    }
 
	    
	    
	    
	    
	    
	    ////////////////////////////////////////////////////////////////////////////
	    
	    /*MÉTODO PARA AGREGAR UN PACIENTE A LA BASE DE DATOS (CONSULTORIO) MYSQL*/
	    private void agregarPac() {
	        String usuario = "root";
	        String password = "matyLucy30!";
	        Scanner sc = new Scanner(System.in);
	        try {
	            System.out.println("Escriba el DNI del Paciente: ");
	            int PacDni = sc.nextInt();
	            sc.nextLine(); 

	            System.out.println("Ingrese el nombre del Paciente: ");
	            String PacNom = sc.nextLine();

	            System.out.println("Ingrese el apellido del Paciente: ");
	            String PacApe = sc.nextLine();

	            System.out.println("Ingrese el domicilio del Paciente: ");
	            String PacDom = sc.nextLine();

	           

	           
	            if (PacNom.isEmpty() || PacApe.isEmpty()||PacDom.isEmpty()) {
	                throw new IllegalArgumentException("El nombre , el apellido o el domicilio estan vacios por favor ingrese de vuelta todo");
	            }

	            
	            String sql = "insert into pacientes (PacDni,PacNom,PacApe,PacDom) values ('" + PacDni + "','" + PacNom + "','" + PacApe + "','" + PacDom + "')";
	            try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/consultorio", usuario, password);
	                 Statement sentencia = con.createStatement()) {
	                int m = sentencia.executeUpdate(sql);
	                if (m == 1)
	                    System.out.println("Se realizó correctamente la inserción: " + sql);
	                else
	                    System.out.println("Falló la inserción");
	            } catch (SQLException e) {
	                System.out.println("Error al conectar con la base de datos.");
	                e.printStackTrace();
	            }
	        } catch (InputMismatchException e) {
	            System.out.println("Error: Debe ingresar un número para el DNI.");
	            sc.nextLine(); 
	        } catch (IllegalArgumentException e) {
	            System.out.println("Error: " + e.getMessage());
	        }
	    }
	    
	    
////////////////////////////////////////////////////////////////////////////
	    
/*MÉTODO PARA AGREGAR UN MEDICO A LA BASE DE DATOS (CONSULTORIO) MYSQL*/
	    
	    private void agregarMed() {
	        String usuario = "root";
	        String password = "matyLucy30!";
	        Scanner sc = new Scanner(System.in);
	        try {
	            System.out.println("Escriba la matricula del medico: ");
	            int MedMat = sc.nextInt();
	            sc.nextLine(); 

	            System.out.println("Ingrese el nombre del Medico: ");
	            String MedNom= sc.nextLine();

	            System.out.println("Ingrese el apellido del Medico: ");
	            String MedApe = sc.nextLine();

	            System.out.println("Ingrese la especialidad del medico: ");
	            String MedEsp = sc.nextLine();

	           

	           
	            if (MedNom.isEmpty() || MedApe.isEmpty()||MedEsp.isEmpty()) {
	                throw new IllegalArgumentException("El nombre , el apellido o la especialidad estan vacios");
	            }

	            
	            String sql = "insert into medicos (MedMat,MedNom,MedApe,MedEsp) values ('" + MedMat + "','" + MedNom + "','" + MedApe + "','" + MedEsp + "')";
	            try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/consultorio", usuario, password);
	                 Statement sentencia = con.createStatement()) {
	                int m = sentencia.executeUpdate(sql);
	                if (m == 1)
	                    System.out.println("Se realizó correctamente la inserción: " + sql);
	                else
	                    System.out.println("Falló la inserción");
	            } catch (SQLException e) {
	                System.out.println("Error al conectar con la base de datos.");
	                e.printStackTrace();
	            }
	        } catch (InputMismatchException e) {
	            System.out.println("Error: Debe ingresar un número de matricula.");
	            sc.nextLine(); 
	        } catch (IllegalArgumentException e) {
	            System.out.println("Error: " + e.getMessage());
	        }
	    }
	    
	    
	    

	   
	 //-----------------------------------------------------------------------------------------------------
	        
	        /*MÉTODO PARA ELIMINAR UN REGISTRO EN LA TABLA PACIENTES*/
	      
	    
	    private static void eliminarPac() {
	            	String usuario="root";
	                String password="matyLucy30!";
	                Scanner sc = new Scanner(System.in);
	                System.out.println("Escriba el DNI del paciente a eliminar:...");
	                int PacDni  = sc.nextInt(); 
	                
	                String sql ="DELETE FROM pacientes WHERE PacDni = '"+PacDni+"'";
	                Connection con=null;
	                
	                try {
	                	Class.forName("com.mysql.jdbc.Driver");     
	                	con=DriverManager.getConnection("jdbc:mysql://localhost:3306/consultorio", usuario, password);  
	                    Statement sentencia = conexion.createStatement();
	                    sentencia.execute(sql);   
	                    System.out.println("El registro se elimino!!");
	                } catch (Exception e) {  
	                  e.printStackTrace();
	                  System.out.println("Error en el borrado del registro!!");
	                }
	              }
	    
////////////////////////////////////////////////////////////////////////////
	    
 /*MÉTODO PARA ELIMINAR UN REGISTRO EN LA TABLA MEDICOS*/
	      
	    
	    private static void eliminarMed() {
	            	String usuario="root";
	                String password="matyLucy30!";
	                Scanner sc = new Scanner(System.in);
	                System.out.println("Escriba la matricula del medico  a eliminar:...");
	                int MedMat  = sc.nextInt(); 
	                
	                String sql ="DELETE FROM medicos WHERE MedMat = '"+MedMat+"'";
	                Connection con=null;
	                
	                try {
	                	Class.forName("com.mysql.jdbc.Driver");     
	                	con=DriverManager.getConnection("jdbc:mysql://localhost:3306/consultorio", usuario, password);  
	                    Statement sentencia = conexion.createStatement();
	                    sentencia.execute(sql);   
	                    System.out.println("El registro se elimino!!");
	                } catch (Exception e) {  
	                  e.printStackTrace();
	                  System.out.println("Error en el borrado del registro!!");
	                }
	              }
	    
	/////////////////////////////////////////////////////////////////////////////////
	    
	    
	    /*MÉTODO PARA MODIFICAR UN REGISTRO EN LA TABLA PACIENTES*/
	    
	    
	    private static void ModRegPac() {
	        String usuario="root";
	        String password="matyLucy30!";
	        Scanner sc = new Scanner(System.in);
	        System.out.println("Escriba el registro a modificar indicado con numeros a continuacion: ");
	        System.out.println("1 PARA CAMBIAR DNI DEL PACIENTE: ");
	        System.out.println("2 PARA CAMBIAR EL NOMBRE DEL PACIENTE: ");
	        System.out.println("3 PARA CAMBIAR EL APELLIDO DEL PACIENTE: ");	        
	        System.out.println("0 SALIR: ");
	        
	        int opcion = sc.nextInt();

	        do {
	            switch(opcion) {
	            case 1: 
	                System.out.println("Ingrese el dni del paciente a modificar:  ");
	                int PacDni  = sc.nextInt();
	                System.out.println("Ingrese el numero por el cual dese modificarlo:  ");
	                int PacDniNew = sc.nextInt();
	                String sql1 ="SELECT * FROM pacientes WHERE PacDni = '" + PacDni + "'";
	                String sql = "UPDATE pacientes set pacDni = '"+PacDniNew+"' where pacDni = '"+PacDni+"'";
	                Connection con =null;
	                Connection selectStatement = null;
	                try {
	                    Class.forName("com.mysql.jdbc.Driver");     
	                    con=DriverManager.getConnection("jdbc:mysql://localhost:3306/consultorio", usuario, password);  
	                    Statement sentencia = con.createStatement();
	                    Statement busq = conexion.createStatement();
	                    ResultSet busq1 =  busq.executeQuery(sql1);
	                    
	                    if(busq1.next()){
	                        Statement UpdateState = con.createStatement() ;
	                        int columnaAfect = UpdateState.executeUpdate(sql);
	                        if(columnaAfect > 0) {
	                            System.out.println("El registro se modificó correctamente.");
	                        } else {
	                            System.out.println("No se pudo modificar el registro.");
	                        }
	                    } else {
	                        System.out.println("El registro con el DNI " + PacDni + " no existe.");
	                    }
	                    
	                    sentencia.execute(sql);   
	                    System.out.println("El registro se modifico!!");
	                    System.out.println(" ingrese 0 para  SALIR: ");
	                     opcion = sc.nextInt();
	                    
	                    
	                } catch (Exception e) {  
	                    e.printStackTrace();
	                    System.out.println("Error en el modificado del registro!!"); 
	                    System.out.println(" ingrese 0 para  SALIR: ");
	                     opcion = sc.nextInt();
	                }
	                break;
	                
	            case 2: 
	                System.out.println("Ingrese el Nombre del paciente a modificar:  ");
	                String PacNom = sc.next();
	                System.out.println("Ingrese el nombre( solo 1 palabra) por el cual dese modificarlo:  ");
	                String PacNomNew =sc.next();
	                String sql2 ="SELECT * FROM pacientes WHERE PacNom = '" + PacNom + "'";
	                String sql3 = "UPDATE pacientes set PacNom = '"+PacNomNew+"' where PacNom = '"+PacNom+"'";
	                Connection con1=null;

	                try {
	                    Class.forName("com.mysql.jdbc.Driver");     
	                    con=DriverManager.getConnection("jdbc:mysql://localhost:3306/consultorio", usuario, password);  
	                    Statement busq = conexion.createStatement();
	                    ResultSet busq1 =  busq.executeQuery(sql2);

	                    if(busq1.next()){
	                        Statement UpdateState = conexion.createStatement() ;
	                        int columnaAfect = UpdateState.executeUpdate(sql3);
	                        if(columnaAfect > 0) {
	                            System.out.println("El registro se modificó correctamente.");
	                        } else {
	                            System.out.println("No se pudo modificar el registro.");
	                        }
	                    } else {
	                        System.out.println("El registro con el nombre " + PacNom + " no existe.");
	                    }
	                } catch (Exception e) {  
	                    e.printStackTrace();
	                    System.out.println("Error en el modificado del registro!!"); 
	                }
	                System.out.println(" ingrese 0 para  SALIR: ");
	                opcion = sc.nextInt();
	                break;
	                
	            case 3: 
	            	 System.out.println("Ingrese el Apellido del paciente a modificar:  ");
		                String PacApe = sc.next();
		                System.out.println("Ingrese el Apellido( solo 1 palabra) por el cual dese modificarlo:  ");
		                String PacApeNew =sc.next();
		                String sql23 ="SELECT * FROM pacientes WHERE PacApe = '" + PacApe + "'";
		                String sql33 = "UPDATE pacientes set PacApe = '"+PacApeNew+"' where PacApe = '"+PacApe+"'";
		                

		                try {
		                    Class.forName("com.mysql.jdbc.Driver");     
		                    con=DriverManager.getConnection("jdbc:mysql://localhost:3306/consultorio", usuario, password);  
		                    Statement busq = conexion.createStatement();
		                    ResultSet busq1 =  busq.executeQuery(sql23);

		                    if(busq1.next()){
		                        Statement UpdateState = conexion.createStatement() ;
		                        int columnaAfect = UpdateState.executeUpdate(sql33);
		                        if(columnaAfect > 0) {
		                            System.out.println("El registro se modificó correctamente.");
		                        } else {
		                            System.out.println("No se pudo modificar el registro.");
		                        }
		                    } else {
		                        System.out.println("El registro con el Apellido " + PacApe + " no existe.");
		                    }
		                } catch (Exception e) {  
		                    e.printStackTrace();
		                    System.out.println("Error en el modificado del registro!!"); 
		                }
		               
																			               	                
	            default:
	            	System.out.println(" ingrese 0 Otra vez para  SALIR: ");
	                opcion = sc.nextInt();
	                break;	            	               	                             	           
	            }
	        } while (opcion !=0);
	    } 

	    
	    
	}