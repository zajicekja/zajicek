using Microsoft.Data.SqlClient;
using System;

class Program
{
    static string GetConnectionString()
    {
        Console.Write("Server name: ");             // ZAJICEK\SQLEXPRESS
        string serverName = Console.ReadLine();

        Console.Write("Database: ");                // GameLibrary
        string databaseName = Console.ReadLine();

        string securityOptions = "Trusted_Connection=True;TrustServerCertificate=True;";

        return $"Server={serverName};Database={databaseName};{securityOptions}";
    }

    static void Main(string[] args)
    {
        string connectionString = GetConnectionString();

        using (SqlConnection connection = new SqlConnection(connectionString))
        {
            try
            {
                connection.Open();

                ListTables(connection);
                ListViews(connection);
                ListStoredProcedures(connection);

                bool running = true;

                while (running)
                {
                    Console.Write("\nEnter name of a table, view, procedure ('exit' to quit): ");
                    string objectName = Console.ReadLine();

                    if (objectName.Equals("exit", StringComparison.OrdinalIgnoreCase))
                    {
                        Console.WriteLine("Exiting program...");
                        break;
                    }

                    if (!string.IsNullOrEmpty(objectName))
                    {
                        try
                        {
                            ExecuteStoredProcedure(objectName, connection);
                        }
                        catch (SqlException ex)
                        {
                            Console.WriteLine($"Error: {ex.Message}");
                            continue;
                        }
                        catch (Exception ex)
                        {
                            Console.WriteLine($"Unexpected error: {ex.Message}");
                            continue;
                        }
                    }
                    else
                    {
                        Console.WriteLine("Invalid input. ");
                    }
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine($"Error: {ex.Message}");
            }
        }
    }

    static void ListTables(SqlConnection connection)
    {
        string query = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE = 'BASE TABLE'";
        using (SqlCommand command = new SqlCommand(query, connection))
        using (SqlDataReader reader = command.ExecuteReader())
        {
            Console.WriteLine("\nTables:");
            while (reader.Read())
            {
                Console.WriteLine(" - " + reader["TABLE_NAME"]);
            }
        }
    }

    static void ListViews(SqlConnection connection)
    {
        string query = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.VIEWS";
        using (SqlCommand command = new SqlCommand(query, connection))
        using (SqlDataReader reader = command.ExecuteReader())
        {
            Console.WriteLine("\nViews:");
            while (reader.Read())
            {
                Console.WriteLine(" - " + reader["TABLE_NAME"]);
            }
        }
    }

    static void ListStoredProcedures(SqlConnection connection)
    {
        string query = "SELECT name FROM sys.procedures";
        using (SqlCommand command = new SqlCommand(query, connection))
        using (SqlDataReader reader = command.ExecuteReader())
        {
            Console.WriteLine("\nStored Procedures:");
            while (reader.Read())
            {
                Console.WriteLine(" - " + reader["name"]);
            }
        }
    }

    static void ExecuteStoredProcedure(string procedureName, SqlConnection connection)
    {
        Console.WriteLine($"\nExecuting stored procedure: {procedureName}");
        using (SqlCommand command = new SqlCommand(procedureName, connection))
        {
            command.CommandType = System.Data.CommandType.StoredProcedure;
            using (SqlDataReader reader = command.ExecuteReader())
            {
                PrintDataReader(reader);
            }
        }
    }

    static void PrintDataReader(SqlDataReader reader)
    {
        if (reader.HasRows)
        {
            for (int i = 0; i < reader.FieldCount; i++)
            {
                Console.Write($"{reader.GetName(i),-20}");
            }
            Console.WriteLine();

            Console.WriteLine(new string('-', 20 * reader.FieldCount));

            while (reader.Read())
            {
                for (int i = 0; i < reader.FieldCount; i++)
                {
                    Console.Write($"{reader[i],-20}");
                }
                Console.WriteLine();
            }
        }
        else
        {
            Console.WriteLine("No data found.");
        }
    }
}