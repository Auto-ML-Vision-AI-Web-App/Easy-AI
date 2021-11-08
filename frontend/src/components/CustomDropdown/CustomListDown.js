import React, { useEffect } from 'react';
import { makeStyles } from '@material-ui/core/styles';
import ListSubheader from '@material-ui/core/ListSubheader';
import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import ListItemText from '@material-ui/core/ListItemText';
import Collapse from '@material-ui/core/Collapse';
import InboxIcon from '@material-ui/icons/MoveToInbox';
import DraftsIcon from '@material-ui/icons/Drafts';
import SendIcon from '@material-ui/icons/Send';
import ExpandLess from '@material-ui/icons/ExpandLess';
import ExpandMore from '@material-ui/icons/ExpandMore';
import StarBorder from '@material-ui/icons/StarBorder';
import CheckCircleOutlineIcon from '@material-ui/icons/CheckCircleOutline';
import HighlightOffIcon from '@material-ui/icons/HighlightOff';

const useStyles = makeStyles((theme) => ({
    root: {
        width: '100%',
        maxWidth: 500,
        backgroundColor: theme.palette.background.paper,
    },
    nested: {
        paddingLeft: theme.spacing(4),
    },
}));

export default function CustomListDown(props) {
    const classes = useStyles();
    const [open, setOpen] = React.useState(true);

    const handleClick = () => {
        setOpen(!open);
    };

    return (
        <List
            component="nav"
            aria-labelledby="nested-list-subheader"
            subheader={
                <ListSubheader component="div" id="nested-list-subheader">
                    업로드된 데이터 정보
                </ListSubheader>
            }
            className={classes.root}
        >
            <CollapseList id="success" dataset={props.dataset} icon={CheckCircleOutlineIcon}></CollapseList>
            <CollapseList id="fail" dataset={props.dataset}></CollapseList>
        </List>
    );
}

function CollapseList(props) {
    const classes = useStyles();
    const [open, setOpen] = React.useState(true);
    const [icon, setIcon] = React.useState(props.id === "success" ? <CheckCircleOutlineIcon></CheckCircleOutlineIcon> : <HighlightOffIcon></HighlightOffIcon>)
    const [subTitle, setSubTitle] = React.useState(props.id === "success" ? "업로드에 성공한 데이터" : "업로드에 실패한 데이터");
    const [dataset, setDataset] = React.useState(props.dataset);

    const handleClick = () => {
        setOpen(!open);
    };


    useEffect(() => {
        console.log(props.dataset);
        return () => {
        };
    });


    return (
        <>
            <ListItem style={{background:'#EDEDED'}} button onClick={handleClick}>
                <ListItemIcon>
                    {icon}
                </ListItemIcon>
                <ListItemText primary={subTitle} />
                {open ? <ExpandLess /> : <ExpandMore />}
            </ListItem>
            <Collapse in={open} timeout="auto" unmountOnExit>
                <List component="div" disablePadding>
                        {dataset.map((item, idx) =>
                            props.id==="success"?
                            item.successList.map((img, imgIdx)=>
                            <ListItem button className={classes.nested}>
                                <ListItemText key={imgIdx} primary={item.className+": "+img}></ListItemText>
                            </ListItem>)
                            :
                            item.failList.map((img, imgIdx)=>
                            <ListItem button className={classes.nested}>
                            <ListItemText key={imgIdx} primary={item.className+": "+img}></ListItemText>
                            </ListItem>)
                        )}
                </List>
            </Collapse>
        </>
    );
}



