package window;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import com.formdev.flatlaf.FlatDarkLaf;

import DTO.DataDTO;

import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.awt.event.ActionEvent;

public class Main {

    private JFrame frame;

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

        JLabel lblFechaCreacion = new JLabel("Fecha de Creación:");
        lblFechaCreacion.setFont(new Font("Calibri", Font.PLAIN, 24));
        lblFechaCreacion.setForeground(Color.WHITE);

        JTextField txtFechaCreacion = new JTextField();
        txtFechaCreacion.setFont(new Font("Calibri", Font.PLAIN, 24));
        
        JLabel lblRegister = new JLabel("");
        lblRegister.setFont(new Font("Calibri", Font.PLAIN, 24));
        lblRegister.setForeground(Color.WHITE);

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

        panel.add(lblFechaCreacion);
        panel.add(txtFechaCreacion);
        panel.add(lblRegister);
        
        JButton btnRegistrar = new JButton("Registrar");
        btnRegistrar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		
        		DataDTO data = new DataDTO();
        		
                data.setName(txtNombreProducto.getText());
                data.setDescription(txtDescripcion.getText());
                data.setCategory(txtCategoria.getText());
                data.setProvider(txtProveedor.getText());
                data.setFechaCreacion(txtFechaCreacion.getText());

                try {
                    // Asignar valores que requieren conversión
                    int stock = Integer.parseInt(txtStock.getText());
                    data.setStock(stock);

                    // Uso del constructor de BigDecimal para convertir de texto a BigDecimal
                    BigDecimal precio = new BigDecimal(txtPrecio.getText());
                    data.setPrice(precio);

                }
                catch (NumberFormatException ex) {
                    // Manejo de excepciones por entrada inválida
                    System.err.println("Error en la conversión de datos: " + ex.getMessage());

                    // Aquí puedes mostrar un mensaje emergente o diálogo de error al usuario para notificar la entrada incorrecta
                    JOptionPane.showMessageDialog(null, "Error: Ingrese valores numéricos válidos para stock y precio.", "Error de conversión", JOptionPane.ERROR_MESSAGE);
                }
                
        	}
        });
        btnRegistrar.setFont(new Font("Calibri", Font.PLAIN, 24));
        panel.add(btnRegistrar);
        
        
    }

    private void agregarTablaConFormulario(JPanel panel) {
        // Create table model with columns for inventory attributes
        DefaultTableModel tableModel = new DefaultTableModel(
            new Object[]{"ID", "Nombre", "Descripción", "Stock", "Precio", "Categoría", "Proveedor"}, 0);

        JTable table = new JTable(tableModel);
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));
        table.setBackground(new Color(64, 64, 64));
        table.setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(800, 200)); // Reduce the height of the table
        panel.add(scrollPane, BorderLayout.CENTER);

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
        btnModificar.setFont(new Font("SansSerif", Font.BOLD, 16));
        btnModificar.setForeground(Color.WHITE);
        formPanel.add(btnModificar);

        panel.add(formPanel, BorderLayout.SOUTH);
    }

    private void agregarTablaEliminar(JPanel panel) {
        // Table model for the Eliminar tab
        DefaultTableModel tableModel = new DefaultTableModel(
            new Object[]{"ID", "Nombre", "Descripción", "Stock", "Precio", "Categoría", "Proveedor"}, 0);

        JTable table = new JTable(tableModel);
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));
        table.setBackground(new Color(64, 64, 64));
        table.setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Button to delete selected items
        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.setFont(new Font("SansSerif", Font.BOLD, 16));
        btnEliminar.setForeground(Color.WHITE);
        btnEliminar.setBackground(new Color(128, 0, 0)); // Dark red for the delete button

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(64, 64, 64)); // Dark background for the button panel
        buttonPanel.add(btnEliminar);

        panel.add(buttonPanel, BorderLayout.SOUTH);
    }
}
