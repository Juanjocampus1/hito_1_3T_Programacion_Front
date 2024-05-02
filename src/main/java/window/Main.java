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
import java.awt.event.ActionEvent;

public class Main {

	private DefaultTableModel tableModel;
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
    	// Inicializar tableModel para que no sea null
    	tableModel = new DefaultTableModel(
                new Object[]{"ID", "Nombre", "Descripción", "Stock", "Precio", "Categoría", "Proveedor"}, 0
            );
    	
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize(screenSize);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setBounds(100, 100, 1920, 1080);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBackground(new Color(40, 40, 40));

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("SansSerif", Font.BOLD, 18));
        tabbedPane.setForeground(Color.WHITE);
        tabbedPane.setBackground(new Color(40, 40, 40));

        JPanel panelRegistro = new JPanel();
        panelRegistro.setLayout(new GridLayout(0, 2));
        tabbedPane.addTab("Registrar Inventario", panelRegistro);

        JPanel panelModificar = new JPanel(new BorderLayout());
        tabbedPane.addTab("Modificar Inventario", panelModificar);

        JPanel panelEliminar = new JPanel(new BorderLayout());
        tabbedPane.addTab("Eliminar Inventario", panelEliminar);

        frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);

        // Change the background of the tabs
        panelRegistro.setBackground(new Color(40, 40, 40)); 
        panelModificar.setBackground(new Color(24, 24, 24)); 
        panelEliminar.setBackground(new Color(24, 24, 24));
        
        // Asegúrate de que tableModel esté inicializado
        tableModel = new DefaultTableModel(
            new Object[]{"ID", "Nombre", "Descripción", "Stock", "Precio", "Categoría", "Proveedor"}, 0
        );

        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        // Agregar el JScrollPane al panel adecuado

        // Llama a actualizarTabla() para mostrar datos al cargar la aplicación
        actualizarTabla();

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
        ReportGenerator.setBackground(new Color(65, 0, 85));
        
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
        styleButton(btnRegistrar);
        // Configurar el evento para el botón "Registrar"
        btnRegistrar.addActionListener(new ActionListener() {
        	@Override
            public void actionPerformed(ActionEvent e) {
                DataDTO data = new DataDTO();

                String nombre = txtNombreProducto.getText();
                String descripcion = txtDescripcion.getText();
                String categoria = txtCategoria.getText();
                String proveedor = txtProveedor.getText();
                String stockStr = txtStock.getText();
                String precioStr = txtPrecio.getText();

                // Validaciones de campos vacíos
                if (nombre.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "El campo 'Nombre' no puede estar vacío.", "Error de validación", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (descripcion.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "El campo 'Descripción' no puede estar vacío.", "Error de validación", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (categoria.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "El campo 'Categoría' no puede estar vacío.", "Error de validación", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (proveedor.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "El campo 'Proveedor' no puede estar vacío.", "Error de validación", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (stockStr.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "El campo 'Stock' no puede estar vacío.", "Error de validación", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (precioStr.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "El campo 'Precio' no puede estar vacío.", "Error de validación", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                try {
                    int stock = Integer.parseInt(stockStr);
                    if (stock < 0) {
                        JOptionPane.showMessageDialog(null, "El stock no puede ser negativo.", "Error de validación", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    BigDecimal precio = new BigDecimal(precioStr);
                    if (precio.compareTo(BigDecimal.ZERO) < 0) {
                        JOptionPane.showMessageDialog(null, "El precio no puede ser negativo.", "Error de validación", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    data.setName(nombre);
                    data.setDescription(descripcion);
                    data.setCategory(categoria);
                    data.setProvider(proveedor);
                    data.setStock(stock);
                    data.setPrice(precio);

                    // Limpiar campos después de asignación exitosa
                    txtNombreProducto.setText("");
                    txtDescripcion.setText("");
                    txtCategoria.setText("");
                    txtProveedor.setText("");
                    txtStock.setText("");
                    txtPrecio.setText("");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Error: Ingrese valores numéricos válidos para stock y precio.", "Error de conversión", JOptionPane.ERROR_MESSAGE);
                    return;
                } catch (Exception es) {
                    JOptionPane.showMessageDialog(null, "Ocurrió un error inesperado. Intente nuevamente.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Enviar solicitud POST y actualizar la tabla
                PostRequest postRequest = new PostRequest();
                postRequest.sendPostRequest(data);
                actualizarTabla(); // Si es necesario
            }
        });
        btnRegistrar.setFont(new Font("Calibri", Font.PLAIN, 38));
        panel.add(btnRegistrar);
        
        
    }

    public void actualizarTabla() {
        if (tableModel == null) { // Verificar si tableModel es null antes de usarlo
            System.err.println("tableModel es null. No se puede actualizar la tabla.");
            return;
        }

        GetRequest getRequest = new GetRequest();
        getRequest.setOnDataReceivedListener(new GetRequest.OnDataReceivedListener() {
            @Override
            public void onDataReceived(JSONArray data) {
                SwingUtilities.invokeLater(() -> {
                    tableModel.setRowCount(0); // Limpiar la tabla antes de agregar nuevos datos

                    for (int i = 0; i < data.length(); i++) {
                        JSONObject jsonObject = data.getJSONObject(i);

                        int id = jsonObject.getInt("id");
                        String nombre = jsonObject.getString("name");
                        String descripcion = jsonObject.getString("description");
                        int stock = jsonObject.getInt("stock");
                        double precio = jsonObject.getDouble("price");
                        String categoria = jsonObject.getString("category");
                        String proveedor = jsonObject.getString("provider");

                        tableModel.addRow(new Object[]{id, nombre, descripcion, stock, precio, categoria, proveedor});
                    }
                });
            }
        });

        getRequest.sendGetRequest(); // Realizar la solicitud GET para obtener datos actualizados
    }

    private void agregarTablaConFormulario(JPanel panel) {
    	// Usar el modelo de tabla existente
        if (tableModel == null) {
            tableModel = new DefaultTableModel(
                new Object[]{"ID", "Nombre", "Descripción", "Stock", "Precio", "Categoría", "Proveedor"}, 0
            );
        }

        // Crear y configurar la JTable
        JTable table = new JTable(tableModel);
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));
        table.setBackground(new Color(64, 64, 64));
        table.setForeground(Color.WHITE);

        // Agregar la tabla a un JScrollPane
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(800, 200)); // Ajustar el tamaño del JScrollPane
        panel.add(scrollPane, BorderLayout.CENTER);

        // Actualizar la tabla al inicio
        actualizarTabla();

        // Crear formulario para modificar inventario
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

        // Otros campos para descripción, stock, precio, categoría, proveedor
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

        // Añadir campos al formulario
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

        // Botón para modificar inventario
        JButton btnModificar = new JButton("Modificar");
        styleButton(btnModificar);
        btnModificar.addActionListener(e -> {
            try {
                // Crear el objeto DataDTO
                DataDTO data = new DataDTO();

                long id = Long.parseLong(txtIdProducto.getText()); // Conversión de texto a long
                data.setId(id);
                data.setName(txtNombreProducto.getText());
                data.setDescription(txtDescripcion.getText());
                data.setCategory(txtCategoria.getText());
                data.setProvider(txtProveedor.getText());

                int stock = Integer.parseInt(txtStock.getText());
                data.setStock(stock);

                BigDecimal precio = new BigDecimal(txtPrecio.getText());
                data.setPrice(precio);

                // Enviar la solicitud PUT para modificar datos
                PutRequest putRequest = new PutRequest();
                putRequest.sendPutRequest(data, id); // Usar el ID largo

                // Actualizar la tabla después de eliminar
                actualizarTabla();

            } catch (NumberFormatException ex) {
                // Manejo de excepciones para valores inválidos
                JOptionPane.showMessageDialog(null, 
                    "Por favor, ingrese valores numéricos válidos para el ID, stock y precio.",
                    "Error de conversión", 
                    JOptionPane.ERROR_MESSAGE
                );
            }
        });

        btnModificar.setFont(new Font("SansSerif", Font.BOLD, 16));
        btnModificar.setForeground(Color.WHITE);
        formPanel.add(btnModificar); // Añadir el botón al panel de formulario

        // Agregar el panel de formulario al panel principal
        panel.add(formPanel, BorderLayout.SOUTH);
    }
    
    public void agregarTablaEliminar(JPanel panel) {
        // Usar el modelo de tabla existente
        if (tableModel == null) {
            tableModel = new DefaultTableModel(
                new Object[]{"ID", "Nombre", "Descripción", "Stock", "Precio", "Categoría", "Proveedor"}, 0
            );
        }

        // Crear y configurar la JTable
        JTable table = new JTable(tableModel);
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));
        table.setBackground(new Color(64, 64, 64));
        table.setForeground(Color.WHITE);

        // Agregar la tabla a un JScrollPane
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(800, 200)); // Ajustar el tamaño del JScrollPane
        panel.add(scrollPane, BorderLayout.CENTER);

        // Botón para eliminar elementos seleccionados
        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.addActionListener(e -> {
            try {
                // Obtener el ID a eliminar
                Long id = Long.parseLong(txtId.getText());

                // Crear solicitud para eliminar
                DeleteRequest deleteRequest = new DeleteRequest();
                deleteRequest.sendDeleteRequest(id);

                // Actualizar la tabla después de eliminar
                actualizarTabla();

                System.out.println("Elemento con ID " + id + " eliminado.");

            } catch (NumberFormatException ex) {
                System.err.println("Error al convertir ID: " + ex.getMessage());
                JOptionPane.showMessageDialog(
                    null, 
                    "Por favor, ingrese un ID válido", 
                    "Error de conversión", 
                    JOptionPane.ERROR_MESSAGE
                );
            }
        });

        // Estilo del botón "Eliminar"
        btnEliminar.setFont(new Font("SansSerif", Font.BOLD, 16));
        btnEliminar.setForeground(Color.WHITE);
        btnEliminar.setBackground(new Color(128, 0, 0)); // Rojo oscuro para el botón de eliminar

        // Panel para botones y campos
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(64, 64, 64)); // Fondo oscuro
        
        // Campo de texto para ingresar ID a eliminar
        JLabel lblID = new JLabel("ID");
        lblID.setFont(new Font("Tahoma", Font.PLAIN, 20));
        buttonPanel.add(lblID);

        txtId = new JTextField();
        txtId.setFont(new Font("Tahoma", Font.PLAIN, 20));
        txtId.setColumns(10); // Tamaño del campo de texto
        buttonPanel.add(txtId);
        buttonPanel.add(btnEliminar);

        // Agregar el panel de botones
        panel.add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private void styleButton(JButton button) {
        button.setBackground(new Color(30, 144, 255)); // Azul para botones de acción positiva
        button.setForeground(Color.WHITE);
        button.setFont(new Font("SansSerif", Font.PLAIN, 18));
    }
}