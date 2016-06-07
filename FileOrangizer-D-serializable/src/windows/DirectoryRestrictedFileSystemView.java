package windows;

import java.io.File;
import java.io.IOException;

import javax.swing.filechooser.FileSystemView;

public class DirectoryRestrictedFileSystemView extends FileSystemView
{
	File root;
	File[] roots = new File[1];

	public DirectoryRestrictedFileSystemView(File root)
	{
		super();
		this.root = root;
		roots[0] = root;
	}

	@Override
	public File createNewFolder(File containingDir)
	{
		File folder = new File(containingDir, "New Folder");
		folder.mkdir();
		return folder;
	}

	@Override
	public File getDefaultDirectory()
	{
		return root;
	}

	@Override
	public File getHomeDirectory()
	{
		return root;
	}

	@Override
	public File[] getRoots()
	{
		return roots;
	}
}
