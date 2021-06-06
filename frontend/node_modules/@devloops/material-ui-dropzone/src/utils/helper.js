window.URL = window.URL || window.webkitURL;

export const fileReader = async file => {
	let fileData = await readFile(file);
	return fileData;
};
const readFile = inputFile => {
	const temporaryFileReader = new FileReader();

	return new Promise((resolve, reject) => {
		temporaryFileReader.onerror = () => {
			temporaryFileReader.abort();
			reject(new DOMException("Problem parsing input file."));
		};

		temporaryFileReader.onload = () => {
			resolve(temporaryFileReader.result);
		};
		temporaryFileReader.readAsDataURL(inputFile);
	});
};
export const sizeConverter = (bytes, si) => {
	var thresh = si ? 1000 : 1024;
	if (Math.abs(bytes) < thresh) {
		return bytes + " B";
	}
	var units = si
		? ["kB", "MB", "GB", "TB", "PB", "EB", "ZB", "YB"]
		: ["KiB", "MiB", "GiB", "TiB", "PiB", "EiB", "ZiB", "YiB"];
	var u = -1;
	do {
		bytes /= thresh;
		++u;
	} while (Math.abs(bytes) >= thresh && u < units.length - 1);
	return bytes.toFixed(1) + " " + units[u];
};
export const mimeType = () => ({
	audio: "library_music",
	application: "format_shapes",
	font: "all_out",
	text: "text_format",
	video: "play_circle_outline"
});
