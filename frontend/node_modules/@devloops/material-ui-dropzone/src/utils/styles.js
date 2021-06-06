export const DropzoneAreaStyle = theme => ({
  fullWidth: {
    width: "100%",
    height: "100%"
  },
  relative: {
    position: "relative"
  },
  dropzone: {
    display: "flex",
    justifyContent: "space-evenly",
    alignContent: "center",
    alignItems: "center",
    flexFlow: "column wrap",
    backgroundColor: "#f4f4f4",
    transition: "all .15s ease-in-out",
    width: "100%",
    padding: theme.spacing(4)
  },
  dropzone_banner: {
    display: "flex",
    justifyContent: "space-evenly",
    alignContent: "center",
    alignItems: "center",
    flexFlow: "column wrap"
  },
  round: {
    borderRadius: theme.spacing(3)
  },
  dropzone_iconBtn: {
    margin: theme.spacing(3, 0)
  },
  dropzone_icon: {
    fontSize: 48,
    color: "deepBlue"
  },
  dropzone_is_dragover: { backgroundColor: "#EAF2F8 !important", border: "2px dashed #666 !important" },
  dropzone_is_dragover_danger: { backgroundColor: "#F5B7B1", border: "2px dashed red" },
  dropzone_overlay: {
    backgroundColor: "rgba(0,0,0,.75)",
    position: "absolute",
    left: 0,
    right: 0,
    top: 0,
    bottom: 0,
    display: "flex",
    justifyContent: "center",
    alignContent: "center",
    alignItems: "center",
    flexFlow: "column wrap"
  }
});
export const DropzoneCardStyle = theme => ({
  fullWidth: {
    width: "100%",
    height: "100%"
  },
  relative: {
    position: "relative"
  },
  dropzone: {
    minHeight: "auto",
    padding: theme.spacing(4)
  },
  dropzone_banner: {
    position: "relative",
    display: "flex",
    justifyContent: "space-evenly",
    alignContent: "center",
    alignItems: "center",
    flexFlow: "column wrap",
    borderRadius: theme.spacing(4)
  },

  round: {
    borderRadius: theme.spacing(4)
  },
  dropzone_iconBtn: {
    margin: theme.spacing(3, 0)
  },
  dropzone_icon: {
    fontSize: 24
  },
  dropzone_is_dragover: { backgroundColor: "#EAF2F8 !important", border: "2px dashed #666 !important" },
  dropzone_is_dragover_danger: { backgroundColor: "#F5B7B1", border: "2px dashed red" },
  dropzone_overlay_img: {
    left: -theme.spacing(4),
    right: -theme.spacing(4),
    top: -theme.spacing(4),
    bottom: -theme.spacing(4),
    height: "auto",
    width: "100%"
  },
  dropzone_topRight_btn: {
    position: "absolute",
    right: 0,
    top: 0,
    backgroundColor: "#fff",
    padding: 8
  }
});

export const FileButtonStyles = theme => ({
  fab: {
    margin: theme.spacing(1)
  },
  extendedIcon: {
    marginRight: theme.spacing(1)
  }
});
