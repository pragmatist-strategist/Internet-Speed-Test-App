package com.pranav.myapplication

import java.math.BigDecimal
import java.math.RoundingMode

data class ProgressionModel(var progressTotal: Float = 0f,
                            var progressDownload: Float = 0f,
                            var progressUpload: Float = 0f,
                            var uploadSpeed: BigDecimal = BigDecimal(0),
                            var downloadSpeed: BigDecimal = BigDecimal(0)
)
// int sum(Node* root, int k) 
// { 
//   //inorder traversal
//   stack<Node*>s;
//   vector<int>v;
//  int count=0,sum=0;
//  while (count<k)
//     {
//         while (root!=  NULL)
//         {
//             s.push(root);
//             root = root->left;
//         }
//         root = s.top();
//         s.pop();
//         count++;
//         sum+= root->data;
//         root = root->right;
//         if(count==k)
//         return sum;
//     } 
// } 
