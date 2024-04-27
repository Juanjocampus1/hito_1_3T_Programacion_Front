package window;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import org.json.JSONArray;
import org.json.JSONObject;

import com.formdev.flatlaf.FlatDarkLaf;

import HTTP.Request.DeleteRequest;
import HTTP.Request.PostRequest;
import HTTP.Request.PutRequest;
import HTTP.Response.GetRequest;
import DTO.DataDTO;

import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.net.URI;
import java.util.Date;
import java.awt.event.ActionEvent;

public class Main {

    private JFrame frame;
    private JTextField txtId;

    public static void main(String[] args) {
        // Apply FlatLaf dark theme
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }

        EventQueue.invokeLater(() -> {
            try {
                Main window = new Main();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public Main() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize(screenSize);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setBounds(100, 100, 1920, 1080);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("SansSerif", Font.BOLD, 18));
        tabbedPane.setForeground(Color.WHITE);

        JPanel panelRegistro = new JPanel();
        panelRegistro.setLayout(new GridLayout(0, 2));
        tabbedPane.addTab("Registrar Inventario", panelRegistro);

        JPanel panelModificar = new JPanel(new BorderLayout());
        tabbedPane.addTab("Modificar Inventario", panelModificar);

        JPanel panelEliminar = new JPanel(new BorderLayout());
        tabbedPane.addTab("Eliminar Inventario", panelEliminar);

        frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);

        // Change the background of the tabs
        panelRegistro.setBackground(new Color(64, 64, 64)); 
        panelModificar.setBackground(new Color(64, 64, 64)); 
        panelEliminar.setBackground(new Color(64, 64, 64)); 

        // Add components to the Registro tab
        agregarFormularioRegistro(panelRegistro);
        
        JButton ReportGenerator = new JButton("Generar informe");
        ReportGenerator.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    // Verifica si el sistema soporta el Desktop
                    if (Desktop.isDesktopSupported()) {
                        Desktop desktop = Desktop.getDesktop();
                        if (desktop.isSupported(Desktop.Action.BROWSE)) {
                            // Abrir la URL deseada
                            URI uri = new URI("http://localhost:8080/hito_1_3t_programacion_web/");
                            desktop.browse(uri);
                        } 
                        else {
                            System.out.println("La acción de navegación no es compatible.");
                        }
                    } 
                    else {
                        System.out.println("Desktop no es compatible.");
                    }
                } 
                catch (Exception ex) {
                    ex.printStackTrace(); // Imprime cualquier error para depuración
                }
            }
        });
        ReportGenerator.setFont(new Font("Calibri", Font.PLAIN, 28));
        panelRegistro.add(ReportGenerator);
        
        // Add table and form to the Modificar tab
        agregarTablaConFormulario(panelModificar);

        // Add table and functionality for the Eliminar tab
        agregarTablaEliminar(panelEliminar);
        
    }

    private void agregarFormularioRegistro(JPanel panel) {
        // Add fields to the registration form
        JLabel lblNombreProducto = new JLabel("Nombre del Producto:");
        lblNombreProducto.setFont(new Font("Calibri", Font.PLAIN, 24));
        lblNombreProducto.setForeground(Color.WHITE);

        JTextField txtNombreProducto = new JTextField();
        txtNombreProducto.setFont(new Font("Calibri", Font.PLAIN, 24));

        JLabel lblDescripcion = new JLabel("Descripción:");
        lblDescripcion.setFont(new Font("Calibri", Font.PLAIN, 24));
        lblDescripcion.setForeground(Color.WHITE);

        JTextField txtDescripcion = new JTextField();
        txtDescripcion.setFont(new Font("Calibri", Font.PLAIN, 24));

        JLabel lblStock = new JLabel("Stock Disponible:");
        lblStock.setFont(new Font("Calibri", Font.PLAIN, 24));
        lblStock.setForeground(Color.WHITE);

        JTextField txtStock = new JTextField();
        txtStock.setFont(new Font("Calibri", Font.PLAIN, 24));

        JLabel lblPrecio = new JLabel("Precio:");
        lblPrecio.setFont(new Font("Calibri", Font.PLAIN, 24));
        lblPrecio.setForeground(Color.WHITE);

        JTextField txtPrecio = new JTextField();
        txtPrecio.setFont(new Font("Calibri", Font.PLAIN, 24));

        JLabel lblCategoria = new JLabel("Categoría:");
        lblCategoria.setFont(new Font("Calibri", Font.PLAIN, 24));
        lblCategoria.setForeground(Color.WHITE);

        JTextField txtCategoria = new JTextField();
        txtCategoria.setFont(new Font("Calibri", Font.PLAIN, 24));

        JLabel lblProveedor = new JLabel("Proveedor:");
        lblProveedor.setFont(new Font("Calibri", Font.PLAIN, 24));
        lblProveedor.setForeground(Color.WHITE);

        JTextField txtProveedor = new JTextField();
        txtProveedor.setFont(new Font("Calibri", Font.PLAIN, 24));

        // Add fields to the panel
        panel.add(lblNombreProducto);
        panel.add(txtNombreProducto);

        panel.add(lblDescripcion);
        panel.add(txtDescripcion);

        panel.add(lblStock);
        panel.add(txtStock);

        panel.add(lblPrecio);
        panel.add(txtPrecio);

        panel.add(lblCategoria);
        panel.add(txtCategoria);

        panel.add(lblProveedor);
        panel.add(txtProveedor);
        
        JButton btnRegistrar = new JButton("Registrar");
        // Configurar el evento para el botón "Registrar"
        btnRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DataDTO data = new DataDTO();

                // Obtener datos desde la interfaz
                data.setName(txtNombreProducto.getText());
                data.setDescription(txtDescripcion.getText());
                data.setCategory(txtCategoria.getText());
                data.setProvider(txtProveedor.getText());

                // Establecer las fechas de creación y actualización

                try {
                    // Asignar valores convertidos y verificar errores de formato
                    int stock = Integer.parseInt(txtStock.getText());
                    data.setStock(stock);

                    BigDecimal precio = new BigDecimal(txtPrecio.getText());
                    data.setPrice(precio);
                    
                    txtNombreProducto.setText("");
                    txtDescripcion.setText("");
                    txtCategoria.setText("");
                    txtProveedor.setText("");
                    txtStock.setText("");
                    txtPrecio.setText("");

                } 
                catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Error: Ingrese valores numéricos válidos para stock y precio.", "Error de conversión", JOptionPane.ERROR_MESSAGE);
                    return; // Salir si hay error
                }

                // Enviar la solicitud POST
                PostRequest postRequest = new PostRequest();
                postRequest.sendPostRequest(data); // Llamar al método para enviar la solicitud POST
            }
            
            
            
        });
        btnRegistrar.setFont(new Font("Calibri", Font.PLAIN, 24));
        panel.add(btnRegistrar);
        
        
    }

    private void agregarTablaConFormulario(JPanel panel) {
    	// Crear el modelo de tabla con las columnas para los atributos del inventario
        DefaultTableModel tableModel = new DefaultTableModel(
                new Object[]{"ID", "Nombre", "Descripción", "Stock", "Precio", "Categoría", "Proveedor"}, 0);

        JTable table = new JTable(tableModel);
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));
        table.setBackground(new Color(64, 64, 64));
        table.setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(800, 200)); // Ajusta según necesites
        panel.add(scrollPane, BorderLayout.CENTER);

        // Crear el Timer para realizar la solicitud GET cada 5 segundos
        int delay = 2500; // 2.5 segundos
        Timer timer = new Timer(delay, e -> {
            GetRequest getRequest = new GetRequest();
            getRequest.setOnDataReceivedListener(new GetRequest.OnDataReceivedListener() {
                @Override
                public void onDataReceived(JSONArray data) {
                    SwingUtilities.invokeLater(() -> {
                        // Limpiar la tabla antes de agregar nuevos datos
                        tableModel.setRowCount(0);

                        // Recorrer el array JSON y agregar los datos a la tabla
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject jsonObject = data.getJSONObject(i);

                            int id = jsonObject.getInt("id");
                            String nombre = jsonObject.getString("name");
                            String descripcion = jsonObject.getString("description");
                            int stock = jsonObject.getInt("stock");
                            double precio = jsonObject.getDouble("price");
                            String categoria = jsonObject.getString("category");
                            String proveedor = jsonObject.getString("provider");

                            // Agregar los datos a la tabla
                            tableModel.addRow(new Object[]{id, nombre, descripcion, stock, precio, categoria, proveedor});
                        }
                    });
                }
            });

            getRequest.sendGetRequest(); // Realizar la solicitud GET
        });

        // Configurar el temporizador para que se repita cada 5 segundos
        timer.setRepeats(true);
        timer.start(); // Iniciar el temporizador

        // Create form to modify inventory
        JPanel formPanel = new JPanel(new GridLayout(0, 2));
        formPanel.setBackground(new Color(64, 64, 64)); 

        JLabel lblIdProducto = new JLabel("ID Producto:");
        lblIdProducto.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblIdProducto.setForeground(Color.WHITE);

        JTextField txtIdProducto = new JTextField();

        JLabel lblNombreProducto = new JLabel("Nombre del Producto:");
        lblNombreProducto.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblNombreProducto.setForeground(Color.WHITE);

        JTextField txtNombreProducto = new JTextField();

        JLabel lblDescripcion = new JLabel("Descripción:");
        lblDescripcion.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblDescripcion.setForeground(Color.WHITE);

        JTextField txtDescripcion = new JTextField();

        JLabel lblStock = new JLabel("Stock Disponible:");
        lblStock.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblStock.setForeground(Color.WHITE);

        JTextField txtStock = new JTextField();

        JLabel lblPrecio = new JLabel("Precio:");
        lblPrecio.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblPrecio.setForeground(Color.WHITE);

        JTextField txtPrecio = new JTextField();

        JLabel lblCategoria = new JLabel("Categoría:");
        lblCategoria.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblCategoria.setForeground(Color.WHITE);

        JTextField txtCategoria = new JTextField();

        JLabel lblProveedor = new JLabel("Proveedor:");
        lblProveedor.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblProveedor.setForeground(Color.WHITE);

        JTextField txtProveedor = new JTextField();

        // Add fields to the form panel
        formPanel.add(lblIdProducto);
        formPanel.add(txtIdProducto);

        formPanel.add(lblNombreProducto);
        formPanel.add(txtNombreProducto);

        formPanel.add(lblDescripcion);
        formPanel.add(txtDescripcion);

        formPanel.add(lblStock);
        formPanel.add(txtStock);

        formPanel.add(lblPrecio);
        formPanel.add(txtPrecio);

        formPanel.add(lblCategoria);
        formPanel.add(txtCategoria);

        formPanel.add(lblProveedor);
        formPanel.add(txtProveedor);

        JButton btnModificar = new JButton("Modificar");
        btnModificar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    // Crear el objeto DataDTO
                    DataDTO data = new DataDTO();

                    // Obtener y convertir el ID (asegúrate de que el ID es un valor numérico)
                    long idLong = Long.parseLong(txtIdProducto.getText());
                    int id = (int) idLong; // Conversión de long a int

                    data.setId((long) id);
                    data.setName(txtNombreProducto.getText());
                    data.setDescription(txtDescripcion.getText());
                    data.setCategory(txtCategoria.getText());
                    data.setProvider(txtProveedor.getText());

                    int stock = Integer.parseInt(txtStock.getText());
                    data.setStock(stock);

                    BigDecimal precio = new BigDecimal(txtPrecio.getText());
                    data.setPrice(precio);

                    // Llamar a la solicitud PUT con un entero
                    PutRequest putRequest = new PutRequest();
                    putRequest.sendPutRequest(data, id);

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(
                        null,
                        "Error: Ingrese valores numéricos válidos para el ID, stock y precio.",
                        "Error de conversión",
                        JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });
        btnModificar.setFont(new Font("SansSerif", Font.BOLD, 16));
        btnModificar.setForeground(Color.WHITE);
        formPanel.add(btnModificar);

        panel.add(formPanel, BorderLayout.SOUTH);
    }

    private void agregarTablaEliminar(JPanel panel) {
    	// Crear el modelo de tabla con las columnas para el nuevo contexto
        DefaultTableModel tableModel = new DefaultTableModel(
                new Object[]{"ID", "Nombre", "Descripción", "Stock", "Precio", "Categoría", "Proveedor"}, 0);

        // Crear y configurar la JTable
        JTable table = new JTable(tableModel);
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));
        table.setBackground(new Color(64, 64, 64));
        table.setForeground(Color.WHITE);

        // Agregar la tabla a un JScrollPane
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(800, 200)); // Ajusta según lo necesario

        // Agregar el JScrollPane al panel
        panel.add(scrollPane, BorderLayout.CENTER);

        // Configurar la solicitud GET con el listener
        GetRequest getRequest = new GetRequest();
        getRequest.setOnDataReceivedListener(new GetRequest.OnDataReceivedListener() {
            @Override
            public void onDataReceived(JSONArray data) {
                SwingUtilities.invokeLater(() -> {
                    // Limpiar la tabla antes de agregar nuevos datos
                    tableModel.setRowCount(0);

                    // Recorrer el array JSON y agregar los datos a la tabla
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject jsonObject = data.getJSONObject(i);

                        int id = jsonObject.getInt("id");
                        String nombre = jsonObject.getString("name");
                        String descripcion = jsonObject.getString("description");
                        int stock = jsonObject.getInt("stock");
                        double precio = jsonObject.getDouble("price");
                        String categoria = jsonObject.getString("category");
                        String proveedor = jsonObject.getString("provider");

                        // Agregar los datos a la tabla
                        tableModel.addRow(new Object[]{id, nombre, descripcion, stock, precio, categoria, proveedor});
                    }
                });
            }
        });

        // Configurar el temporizador para hacer actualizaciones periódicas
        int delay = 2500; // 2.5 segundos
        Timer timer = new Timer(delay, e -> getRequest.sendGetRequest()); // Realizar la solicitud GET
        timer.setRepeats(true); // Hacer que se repita
        timer.start(); // Iniciar el temporizador

        // Button to delete selected items
        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    // Convertir el texto del campo txtId a un número Long
                    Long id = Long.parseLong(txtId.getText());

                    // Crear el objeto DeleteRequest para enviar la solicitud DELETE
                    DeleteRequest deleteRequest = new DeleteRequest();
                    deleteRequest.sendDeleteRequest(id); // Enviar la solicitud DELETE

                    System.out.println("Elemento con ID " + id + " eliminado."); // Mensaje para confirmar la acción

                } catch (NumberFormatException ex) {
                    // Manejar el error si el texto no es un número válido
                    System.err.println("Error al convertir ID: " + ex.getMessage());
                    JOptionPane.showMessageDialog(null, 
                        "Por favor, ingrese un ID válido", 
                        "Error de conversión", 
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        btnEliminar.setFont(new Font("SansSerif", Font.BOLD, 16));
        btnEliminar.setForeground(Color.WHITE);
        btnEliminar.setBackground(new Color(128, 0, 0)); // Dark red for the delete button

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(64, 64, 64)); // Dark background for the button panel
        
        JLabel lblID = new JLabel("ID");
        lblID.setFont(new Font("Tahoma", Font.PLAIN, 20));
        buttonPanel.add(lblID);
        
        txtId = new JTextField();
        txtId.setFont(new Font("Tahoma", Font.PLAIN, 20));
        buttonPanel.add(txtId);
        txtId.setColumns(10);
        buttonPanel.add(btnEliminar);

        panel.add(buttonPanel, BorderLayout.SOUTH);
    }
}