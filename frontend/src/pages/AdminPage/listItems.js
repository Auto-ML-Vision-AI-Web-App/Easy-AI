import React from 'react';
import ListItem from '@material-ui/core/ListItem';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import ListItemText from '@material-ui/core/ListItemText';
import ListSubheader from '@material-ui/core/ListSubheader';
import HomeIcon from '@material-ui/icons/Home';
import PublishIcon from '@material-ui/icons/Publish';
import BarChartIcon from '@material-ui/icons/BarChart';
import AssignmentIcon from '@material-ui/icons/Assignment';
import BuildIcon from '@material-ui/icons/Build';
import TouchAppIcon from '@material-ui/icons/TouchApp';

import { Link } from "react-router-dom";
import './test.css';

export const homeListItems = (
  <div>
    <ListItem className="listItemBtn" button component={Link} to="/">
      <ListItemIcon>
        <HomeIcon />
      </ListItemIcon>
      <ListItemText primary="Home" />
    </ListItem>
  </div>
);

export const mainListItems = (
  <div>
    <ListSubheader inset>Make AI</ListSubheader>
    <ListItem className="listItemBtn" button component={Link} to="/admin/ai-choosing">
      <ListItemIcon>
        <TouchAppIcon />
      </ListItemIcon>
      <ListItemText className="listItemText" primary="AI 선택하기" />
    </ListItem>
    <ListItem className="listItemBtn" button component={Link} to="/admin/data-uploading">
      <ListItemIcon>
        <PublishIcon />
      </ListItemIcon>
      <ListItemText className="listItemText" primary="데이터 업로드하기" />
    </ListItem>
    <ListItem className="listItemBtn" button component={Link} to="/admin/ai-making">
      <ListItemIcon>
        <BuildIcon />
      </ListItemIcon>
      <ListItemText className="listItemText"  primary="AI 만들기" />
    </ListItem>
    <ListItem className="listItemBtn" button component={Link} to="/admin/ai-checking">
      <ListItemIcon>
        <BarChartIcon />
      </ListItemIcon>
      <ListItemText className="listItemText" primary="AI 결과 확인하기" />
    </ListItem>
    {/*<ListItem button component={Link} to="/admin/ai-testing">
      <ListItemIcon>
        <ImageSearchIcon />
      </ListItemIcon>
      <ListItemText className="listItemText" primary="AI 테스트하기" />
</ListItem>*/}
  </div>
);

export const secondaryListItems = (
  <div>
    <ListSubheader inset>My Projects</ListSubheader>
    <ListItem className="listItemBtn" button component={Link} to="/admin/projects">
      <ListItemIcon>
        <AssignmentIcon />
      </ListItemIcon>
      <ListItemText className="listItemText" primary="프로젝트 관리하기" />
    </ListItem>
  </div>
);