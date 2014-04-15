package com.cwalter.trianglepuzzle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

import com.cwalter.trianglepuzzle.PathAnalyzer;
import com.cwalter.trianglepuzzle.ProcessException;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class PathAnalyzerTests {

	private static final String userDir = System.getProperty("user.dir");
	
	private static final String resourcesDir = userDir + "\\src\\test\\resources\\";
	
	private static final String file4Row = resourcesDir + "triangle_test_4rows.txt";
	
	private static final String file100Row = resourcesDir + "triangle_test_100rows.txt";
	
	@Test
	@Category(UnitTests.class)
	public void Main_0Args_ThrowsIllegalArgumentException() {
		//Arrange
		try {
			//Act
			PathAnalyzer.main();
			
			//Assert
			fail("IllegalArgumentException not thrown.");
		} catch(IllegalArgumentException ex) {
		}
	}
	
	@Test
	@Category(UnitTests.class)
	public void Main_2Args_ThrowsIllegalArgumentException() {
		//Arrange
		try {
			
			//Act
			PathAnalyzer.main("a", "b");
			
			//Assert
			fail("IllegalArgumentException not thrown.");
		} catch(IllegalArgumentException ex) {
		}
	}
	
	@Test
	@Category(UnitTests.class)
	public void Constructor_NullOutStream_ThrowsIllegalArgumentException() {
		//Arrange
		PrintStream errorStream = Mockito.mock(PrintStream.class);
		try {
			
			//Act
			new PathAnalyzer(null, errorStream);
			
			//Assert
			fail("IllegalArgumentException not thrown.");
		} catch(IllegalArgumentException ex) {
		}
	}
	
	@Test
	@Category(UnitTests.class)
	public void Constructor_NullErrorStream_ThrowsIllegalArgumentException() {
		//Arrange
		PrintStream outStream = Mockito.mock(PrintStream.class);
		try {
			
			//Act
			new PathAnalyzer(outStream, null);
			
			//Assert
			fail("IllegalArgumentException not thrown.");
		} catch(IllegalArgumentException ex) {
		}
	}
	
	@Test
	@Category(UnitTests.class)
	public void getNext_NullReader_ThrowsIllegalArgumentException() throws IOException {
		//Arrange
		PrintStream outStream = Mockito.mock(PrintStream.class);
		PrintStream errorStream = Mockito.mock(PrintStream.class);
		PathAnalyzer pathAnalyzer = new PathAnalyzer(outStream, errorStream);
		
		try {
			//Act
			pathAnalyzer.getNext(null);
			
			//Assert
			fail("IllegalArgumentException not thrown.");
		} catch(IllegalArgumentException ex) {
			
		}
	}
	
	@Test
	@Category(UnitTests.class)
	public void getNext_EmptyNullReader_ReturnsNull() throws IOException {
		//Arrange
		PrintStream outStream = Mockito.mock(PrintStream.class);
		PrintStream errorStream = Mockito.mock(PrintStream.class);
		PathAnalyzer pathAnalyzer = new PathAnalyzer(outStream, errorStream);
		
		BufferedReader reader = Mockito.mock(BufferedReader.class);
		when(reader.readLine()).thenReturn("", null);
		
		assertNull(pathAnalyzer.getNext(reader));
		verify(reader, times(2)).readLine();
	}
	
	@Test
	@Category(UnitTests.class)
	public void getNext_IntReader_ReturnsInt() throws IOException {
		//Arrange
		PrintStream outStream = Mockito.mock(PrintStream.class);
		PrintStream errorStream = Mockito.mock(PrintStream.class);
		PathAnalyzer pathAnalyzer = new PathAnalyzer(outStream, errorStream);
		
		BufferedReader reader = Mockito.mock(BufferedReader.class);
		when(reader.readLine()).thenReturn("5", null);
		
		assertEquals("5", pathAnalyzer.getNext(reader));
		verify(reader, times(1)).readLine();
		assertNull(pathAnalyzer.getNext(reader));
		verify(reader, times(2)).readLine();
	}
	
	@Test
	@Category(UnitTests.class)
	public void getMaxPathFromReader_NullReader_ThrowsIllegalArgumentException() throws IOException {
		//Arrange
		PrintStream outStream = Mockito.mock(PrintStream.class);
		PrintStream errorStream = Mockito.mock(PrintStream.class);
		PathAnalyzer pathAnalyzer = new PathAnalyzer(outStream, errorStream);
		
		String delimeter = " ";
		
		//Act
		try {
			pathAnalyzer.getMaxPathFromReader(null, delimeter);
			fail("IllegalArgumentException not thrown.");
		} catch(IllegalArgumentException ex) {
			
		}
		
		//Assert
		verify(errorStream, never()).println(anyString());
		verify(outStream, never()).println(anyString());
	}
	
	@Test
	@Category(UnitTests.class)
	public void getMaxPathFromReader_NullDelimeter_ThrowsIllegalArgumentException() throws IOException {
		//Arrange
		PrintStream outStream = Mockito.mock(PrintStream.class);
		PrintStream errorStream = Mockito.mock(PrintStream.class);
		PathAnalyzer pathAnalyzer = new PathAnalyzer(outStream, errorStream);
		
		BufferedReader reader = Mockito.mock(BufferedReader.class);
		
		//Act
		try {
			pathAnalyzer.getMaxPathFromReader(reader, null);
			fail("IllegalArgumentException not thrown.");
		} catch(IllegalArgumentException ex) {
			
		}
		
		//Assert
		verify(errorStream, never()).println(anyString());
		verify(outStream, never()).println(anyString());
	}
	
	@Test
	@Category(UnitTests.class)
	public void getMaxPathFromReader_NullBufferedReader_Returns0() throws IOException {
		//Arrange
		PrintStream outStream = Mockito.mock(PrintStream.class);
		PrintStream errorStream = Mockito.mock(PrintStream.class);
		PathAnalyzer pathAnalyzer = new PathAnalyzer(outStream, errorStream);
		
		BufferedReader reader = Mockito.mock(BufferedReader.class);
		
		String delimeter = " ";
		
		//Act, Assert
		assertEquals(0, pathAnalyzer.getMaxPathFromReader(reader, delimeter));
		verify(errorStream, times(1)).println(anyString());
		verify(outStream, never()).println(anyString());
	}
	
	@Test
	@Category(UnitTests.class)
	public void getMaxPathFromReader_EmptyBufferedReader_Returns0() throws IOException {
		//Arrange
		PrintStream outStream = Mockito.mock(PrintStream.class);
		PrintStream errorStream = Mockito.mock(PrintStream.class);
		PathAnalyzer pathAnalyzer = new PathAnalyzer(outStream, errorStream);
		
		BufferedReader reader = Mockito.mock(BufferedReader.class);
		when(reader.readLine()).thenReturn("", null);
		
		String delimeter = " ";
		
		//Act, Assert
		assertEquals(0, pathAnalyzer.getMaxPathFromReader(reader, delimeter));
		verify(errorStream, times(1)).println(anyString());
		verify(outStream, never()).println(anyString());
	}
	
	@Test
	@Category(UnitTests.class)
	public void getMaxPathFromReader_EmptySpaceBufferedReader_ReturnInt() throws IOException {
		//Arrange
		PrintStream outStream = Mockito.mock(PrintStream.class);
		PrintStream errorStream = Mockito.mock(PrintStream.class);
		PathAnalyzer pathAnalyzer = new PathAnalyzer(outStream, errorStream);
		
		BufferedReader reader = Mockito.mock(BufferedReader.class);
		when(reader.readLine()).thenReturn("    ", null);
		
		String delimeter = " ";
		
		//Act, Assert
		assertEquals(0, pathAnalyzer.getMaxPathFromReader(reader, delimeter));
		verify(errorStream, times(1)).println(anyString());
		verify(outStream, never()).println(anyString());
	}
	
	@Test
	@Category(UnitTests.class)
	public void getMaxPathFromReader_MultipleSpacesBetweenIntsBufferedReader_ReturnInt() throws IOException {
		//Arrange
		PrintStream outStream = Mockito.mock(PrintStream.class);
		PrintStream errorStream = Mockito.mock(PrintStream.class);
		PathAnalyzer pathAnalyzer = new PathAnalyzer(outStream, errorStream);
		
		BufferedReader reader = Mockito.mock(BufferedReader.class);
		when(reader.readLine()).thenReturn(" 4   ", "   24    234   ", null);
		
		String delimeter = " ";
		
		//Act, Assert
		assertEquals(238, pathAnalyzer.getMaxPathFromReader(reader, delimeter));
		verify(errorStream, never()).println(anyString());
		verify(outStream, never()).println(anyString());
	}
	
	@Test
	@Category(UnitTests.class)
	public void getMaxPathFromReader_FirstLineEmptyBufferedReader_Returns0() throws IOException {
		//Arrange
		PrintStream outStream = Mockito.mock(PrintStream.class);
		PrintStream errorStream = Mockito.mock(PrintStream.class);
		PathAnalyzer pathAnalyzer = new PathAnalyzer(outStream, errorStream);
		
		BufferedReader reader = Mockito.mock(BufferedReader.class);
		when(reader.readLine()).thenReturn("", "1", null);
		
		String delimeter = " ";
		
		//Act, Assert
		assertEquals(1, pathAnalyzer.getMaxPathFromReader(reader, delimeter));
		verify(errorStream, never()).println(anyString());
		verify(outStream, never()).println(anyString());
	}
	
	@Test
	@Category(UnitTests.class)
	public void getMaxPathFromReader_SingleStringBufferedReader_ThrowsNumberFormatException() throws IOException {
		//Arrange
		PrintStream outStream = Mockito.mock(PrintStream.class);
		PrintStream errorStream = Mockito.mock(PrintStream.class);
		PathAnalyzer pathAnalyzer = new PathAnalyzer(outStream, errorStream);
		
		BufferedReader reader = Mockito.mock(BufferedReader.class);
		when(reader.readLine()).thenReturn("string");
		
		String delimeter = " ";
		
		try {
			//Act
			pathAnalyzer.getMaxPathFromReader(reader, delimeter);
			
			//Assert
			fail("NumberFormatException was not thrown.");
		} catch(NumberFormatException ex) {
		}
	}
	
	@Test
	@Category(UnitTests.class)
	public void getMaxPathFromReader_MultipleStringBufferedReader_ThrowsNumberFormatException() throws IOException {
		//Arrange
		PrintStream outStream = Mockito.mock(PrintStream.class);
		PrintStream errorStream = Mockito.mock(PrintStream.class);
		PathAnalyzer pathAnalyzer = new PathAnalyzer(outStream, errorStream);
		
		BufferedReader reader = Mockito.mock(BufferedReader.class);
		when(reader.readLine()).thenReturn("string and another string");
		
		String delimeter = " ";
		
		try {
			//Act
			pathAnalyzer.getMaxPathFromReader(reader, delimeter);
			
			//Assert
			fail("NumberFormatException was not thrown.");
		} catch(NumberFormatException ex) {
		}
	}
	
	@Test
	@Category(UnitTests.class)
	public void getMaxPathFromReader_MixedIntStringBufferedReader_ThrowsNumberFormatException() throws IOException {
		//Arrange
		PrintStream outStream = Mockito.mock(PrintStream.class);
		PrintStream errorStream = Mockito.mock(PrintStream.class);
		PathAnalyzer pathAnalyzer = new PathAnalyzer(outStream, errorStream);
		
		BufferedReader reader = Mockito.mock(BufferedReader.class);
		when(reader.readLine()).thenReturn("9 50 string 40");
		
		String delimeter = " ";
		
		try {
			//Act
			pathAnalyzer.getMaxPathFromReader(reader, delimeter);
			
			//Assert
			fail("NumberFormatException was not thrown.");
		} catch(NumberFormatException ex) {
		}
	}
	
	@Test
	@Category(UnitTests.class)
	public void getMaxPathFromReader_MixedIntFloatBufferedReader_ThrowsNumberFormatException() throws IOException {
		//Arrange
		PrintStream outStream = Mockito.mock(PrintStream.class);
		PrintStream errorStream = Mockito.mock(PrintStream.class);
		PathAnalyzer pathAnalyzer = new PathAnalyzer(outStream, errorStream);
		
		BufferedReader reader = Mockito.mock(BufferedReader.class);
		when(reader.readLine()).thenReturn("9 50 3.5 40");
		
		String delimeter = " ";
		
		try {
			//Act
			pathAnalyzer.getMaxPathFromReader(reader, delimeter);
			
			//Assert
			fail("NumberFormatException was not thrown.");
		} catch(NumberFormatException ex) {
		}
	}
	
	@Test
	@Category(UnitTests.class)
	public void getMaxPathFromReader_SingleIntBufferedReader_ReturnInt() throws IOException {
		//Arrange
		PrintStream outStream = Mockito.mock(PrintStream.class);
		PrintStream errorStream = Mockito.mock(PrintStream.class);
		PathAnalyzer pathAnalyzer = new PathAnalyzer(outStream, errorStream);
		
		BufferedReader reader = Mockito.mock(BufferedReader.class);
		when(reader.readLine()).thenReturn("42", null);
		
		String delimeter = " ";
		
		//Act, Assert
		assertEquals(42, pathAnalyzer.getMaxPathFromReader(reader, delimeter));
		verify(errorStream, never()).println(anyString());
		verify(outStream, never()).println(anyString());
	}
	
	@Test
	@Category(UnitTests.class)
	public void getMaxPathFromReader_MultipleIntBufferedReader_ReturnInt() throws IOException {
		//Arrange
		PrintStream outStream = Mockito.mock(PrintStream.class);
		PrintStream errorStream = Mockito.mock(PrintStream.class);
		PathAnalyzer pathAnalyzer = new PathAnalyzer(outStream, errorStream);
		
		BufferedReader reader = Mockito.mock(BufferedReader.class);
		when(reader.readLine()).thenReturn("1", "2 3", null);
		
		String delimeter = " ";
		
		//Act, Assert
		assertEquals(4, pathAnalyzer.getMaxPathFromReader(reader, delimeter));
		verify(errorStream, never()).println(anyString());
		verify(outStream, never()).println(anyString());
	}
	
	
	@Test
	@Category(UnitTests.class)
	public void getMaxPathFromReader_MultiLineIntStringBufferedReader_ReturnInt() throws IOException {
		//Arrange
		PrintStream outStream = Mockito.mock(PrintStream.class);
		PrintStream errorStream = Mockito.mock(PrintStream.class);
		PathAnalyzer pathAnalyzer = new PathAnalyzer(outStream, errorStream);
		
		BufferedReader reader = Mockito.mock(BufferedReader.class);
		when(reader.readLine()).thenReturn("1", "string two", null);
		
		String delimeter = " ";
		
		try {
			//Act
			pathAnalyzer.getMaxPathFromReader(reader, delimeter);
			
			//Assert
			fail("NumberFormatException was not thrown.");
		} catch(NumberFormatException ex) {
		}
	}
	
	@Test
	@Category(UnitTests.class)
	public void getMaxPathFromFile_NullFile_ThrowsIllegalArgumentException() throws ProcessException {
		//Arrange
		PrintStream outStream = Mockito.mock(PrintStream.class);
		PrintStream errorStream = Mockito.mock(PrintStream.class);
		PathAnalyzer pathAnalyzer = new PathAnalyzer(outStream, errorStream);
		
		String delimeter = " ";
		
		try {
			//Act
			pathAnalyzer.getMaxPathFromFile(null, delimeter);
			
			//Assert
			fail("IllegalArgumentException was not thrown.");
		} catch(IllegalArgumentException ex) {
		}
	}
	
	@Test
	@Category(UnitTests.class)
	public void getMaxPathFromFile_NonExistentFile_ThrowsIllegalArgumentException() {
		//Arrange
		PrintStream outStream = Mockito.mock(PrintStream.class);
		PrintStream errorStream = Mockito.mock(PrintStream.class);
		PathAnalyzer pathAnalyzer = new PathAnalyzer(outStream, errorStream);
		
		String delimeter = " ";
		
		try {
			//Act
			pathAnalyzer.getMaxPathFromFile("", delimeter);
			
			//Assert
			fail("ProcessException was not thrown.");
		} catch(ProcessException ex) {
		}
	}
	
	@Test
	@Category(UnitTests.class)
	public void getMaxPathFromFile_NullDelimeter_ThrowsIllegalArgumentException() throws ProcessException {
		//Arrange
		PrintStream outStream = Mockito.mock(PrintStream.class);
		PrintStream errorStream = Mockito.mock(PrintStream.class);
		PathAnalyzer pathAnalyzer = new PathAnalyzer(outStream, errorStream);
		
		try {
			//Act
			pathAnalyzer.getMaxPathFromFile(" ", null);
			
			//Assert
			fail("IllegalArgumentException was not thrown.");
		} catch(IllegalArgumentException ex) {
		}
	}
	
	@Test
	@Category(FileTests.class)
	public void getMaxPathFromFile_4RowFile() throws ProcessException {
		//Arrange
		PrintStream outStream = Mockito.mock(PrintStream.class);
		PrintStream errorStream = Mockito.mock(PrintStream.class);
		PathAnalyzer pathAnalyzer = new PathAnalyzer(outStream, errorStream);
		
		String delimeter = " ";
		
		//Act, Assert
		assertEquals(27, pathAnalyzer.getMaxPathFromFile(file4Row, delimeter));
		verify(outStream, never()).println(anyString());
		verify(errorStream, never()).println(anyString());
	}
	
	@Test
	@Category(FileTests.class)
	public void getMaxPathFromFile_100RowFile() throws ProcessException {
		//Arrange
		PrintStream outStream = Mockito.mock(PrintStream.class);
		PrintStream errorStream = Mockito.mock(PrintStream.class);
		PathAnalyzer pathAnalyzer = new PathAnalyzer(outStream, errorStream);
		
		String delimeter = " ";
		
		//Act, Assert
		assertEquals(732506, pathAnalyzer.getMaxPathFromFile(file100Row, delimeter));
		verify(outStream, never()).println(anyString());
		verify(errorStream, never()).println(anyString());
	}
	
	@Test
	@Category(UnitTests.class)
	public void printMaxPath_WritesMaxPath() {
		//Arrange
		PrintStream outStream = Mockito.mock(PrintStream.class);
		PrintStream errorStream = Mockito.mock(PrintStream.class);
		PathAnalyzer pathAnalyzer = new PathAnalyzer(outStream, errorStream);
		
		//Act
		pathAnalyzer.printMaxPath(50);
		
		//Assert
		verify(errorStream, never()).println(anyString());
		verify(outStream, times(1)).println(anyString());
	}
}
