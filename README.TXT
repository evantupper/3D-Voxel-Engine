//
            if (!(vel_z + z < -vox.z + player_width + vox.voxel_width && vel_z + z > -vox.z - player_width - vox.voxel_width 
                && vel_x + x < -vox.x + player_width + vox.voxel_width && vel_x + x > -vox.x - player_width - vox.voxel_width 
                && vel_y + y < -vox.y + player_height + vox.voxel_width && vel_y + y > -vox.y - player_height - vox.voxel_width)) {
                continue;
            }

           
            if (y >= -vox.y + player_height + vox.voxel_width) {
                y = -vox.y + player_height + vox.voxel_width;
                vel_y = 0;
            }
            else if (y <= -vox.y - player_height - vox.voxel_width) {
                y = -vox.y - player_height - vox.voxel_width;
                vel_y = 0;
            }

            System.out.println(y + vel_y + " " + -(vox.y + vox.voxel_width + player_height));
            if (x >= -vox.x + player_width + vox.voxel_width
                && //Making sure y is well above or below  
                (
                    y + vel_y < -(vox.y + vox.voxel_width + player_height)
                    && y + vel_y > -(vox.y - vox.voxel_width - player_height)
                )
                )
            {
                x = -vox.x + player_width + vox.voxel_width;
                vel_x = 0;
                System.out.println("Collides x");
            }
            else if (x <= -vox.x - player_width - vox.voxel_width
                && //Making sure y is above or below  
                !(
                    y >= -vox.y + vox.voxel_width + player_height 
                    || y <= -vox.y - vox.voxel_width - player_height 
                )
                ) 
            {
                x = -vox.x - player_width - vox.voxel_width;
                vel_x = 0;
                System.out.println("Collides x");
            }

            if (z > -vox.z + player_width + vox.voxel_width  && (y> -vox.y + player_height + vox.voxel_width && y < -vox.y + player_height + vox.voxel_width)) {
                z = -vox.z + player_width + vox.voxel_width;
                vel_z = 0;
                System.out.println("Collides z");
            }
            else if (z < -vox.z - player_width - vox.voxel_width  && (y > -vox.y + player_height + vox.voxel_width && y < -vox.y + player_height + vox.voxel_width)) {
                z = -vox.z - player_width - vox.voxel_width;
                vel_z = 0;
                System.out.println("Collides z");
            }
            
            
            
        }

        if (!isGrounded)
            vel_y += 0.02f;
        else if (isGrounded)
            vel_y = 0;

        if (vel_x > 0)
            vel_x -= 0.005f;
        else if (vel_x < 0)
            vel_x += 0.005f;
        if (vel_z > 0)
            vel_z -= 0.005f;
        else if (vel_z < 0)
            vel_z += 0.005f;